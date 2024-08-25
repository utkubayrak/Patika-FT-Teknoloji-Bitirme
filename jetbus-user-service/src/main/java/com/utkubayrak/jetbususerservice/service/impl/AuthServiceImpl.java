package com.utkubayrak.jetbususerservice.service.impl;

import com.utkubayrak.jetbususerservice.dto.request.*;
import com.utkubayrak.jetbususerservice.dto.response.LoginResponse;
import com.utkubayrak.jetbususerservice.dto.response.RegisterResponse;
import com.utkubayrak.jetbususerservice.exception.ExceptionMessages;
import com.utkubayrak.jetbususerservice.exception.UserException;
import com.utkubayrak.jetbususerservice.model.User;
import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;
import com.utkubayrak.jetbususerservice.repository.UserRepository;
import com.utkubayrak.jetbususerservice.security.service.JwtProvider;
import com.utkubayrak.jetbususerservice.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.email.routing.key}")
    private String emailRoutingKey;

    @Value("${rabbitmq.binding.password.routing.key}")
    private String passwordRoutingKey;

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        logger.info("Register request received for email: {}", registerRequest.getEmail());
        boolean isExists = userRepository.existsByEmail(registerRequest.getEmail());
        if (isExists) {
            logger.warn("Registration attempt failed: User with email {} already exists", registerRequest.getEmail());
            throw new UserException(ExceptionMessages.USER_ALREADY_DEFINED);
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        System.out.println(registerRequest.isCorporate());
        if (registerRequest.isCorporate()) {
            user.setRole(RoleEnum.CORPORATE);
        }else{
            user.setRole(RoleEnum.INDIVIDUAL);
        }
        user.setGender(registerRequest.getGender());
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);

        sendVerificationEmail(user);
        userRepository.save(user);

        logger.info("User {} registered successfully, verification email sent", user.getEmail());

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setEmail(user.getEmail());
        registerResponse.setFullName(user.getFullName());
        registerResponse.setRole(user.getRole());
        registerResponse.setGender(user.getGender());
        registerResponse.setMessage("Kayıt başarılı, lütfen e-postanızı onaylayın.");
        return registerResponse;
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        logger.info("Login attempt for email: {}", loginRequest.getEmail());
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            logger.warn("Login failed: User with email {} not found", loginRequest.getEmail());
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }
        if (!user.isEnabled()) {
            logger.warn("Login failed: User with email {} is not verified", loginRequest.getEmail());
            throw new UserException(ExceptionMessages.USER_NOT_VERIFIED);
        }

        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
        String jwt = jwtProvider.generateToken(authentication);

        logger.info("Login successful for email: {}", loginRequest.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(jwt);
        loginResponse.setMessage("Giriş başarılı.");

        return loginResponse;
    }

    @Override
    public void initiatePasswordReset(EmailVerificationRequest emailVerificationRequest) {
        logger.info("Password reset initiation request for email: {}", emailVerificationRequest.getEmail());
        User user = userRepository.findByEmail(emailVerificationRequest.getEmail());
        if (user == null) {
            logger.warn("Password reset initiation failed: User with email {} not found", emailVerificationRequest.getEmail());
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }

        if (user.isEnabled()) {
            String resetCode = generateVerificationCode();
            user.setResetCode(resetCode);
            user.setResetCodeExpiresAt(LocalDateTime.now().plusMinutes(10));
            userRepository.save(user);

            sendPasswordResetEmail(user);
            logger.info("Password reset email sent to {}", user.getEmail());
        } else {
            logger.warn("Password reset initiation failed: User with email {} is not verified", emailVerificationRequest.getEmail());
            throw new UserException(ExceptionMessages.USER_NOT_VERIFIED);
        }
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        logger.info("Password reset attempt for email: {}", passwordResetRequest.getEmail());
        User user = userRepository.findByEmail(passwordResetRequest.getEmail());
        if (user == null) {
            logger.warn("Password reset failed: User with email {} not found", passwordResetRequest.getEmail());
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }

        if (user.getResetCodeExpiresAt().isBefore(LocalDateTime.now())) {
            logger.warn("Password reset failed: Reset code for email {} has expired", passwordResetRequest.getEmail());
            throw new UserException(ExceptionMessages.RESET_CODE_EXPIRED);
        }

        if (!user.getResetCode().equals(passwordResetRequest.getResetCode())) {
            logger.warn("Password reset failed: Invalid reset code for email {}", passwordResetRequest.getEmail());
            throw new UserException(ExceptionMessages.INVALID_RESET_CODE);
        }

        user.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
        user.setResetCode(null);
        user.setResetCodeExpiresAt(null);
        userRepository.save(user);
        logger.info("Password reset successful for email: {}", passwordResetRequest.getEmail());
    }

    @Override
    public void verifyUser(VerifyUserRequest verifyUserRequest) {
        logger.info("User verification attempt for email: {}", verifyUserRequest.getEmail());
        User user = userRepository.findByEmail(verifyUserRequest.getEmail());
        if (user == null) {
            logger.warn("User verification failed: User with email {} not found", verifyUserRequest.getEmail());
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }
        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            logger.warn("User verification failed: Verification code for email {} has expired", verifyUserRequest.getEmail());
            throw new UserException(ExceptionMessages.VERIFICATION_CODE_EXPIRED);
        }
        if (!user.getVerificationCode().equals(verifyUserRequest.getVerificationCode())) {
            logger.warn("User verification failed: Invalid verification code for email {}", verifyUserRequest.getEmail());
            throw new UserException(ExceptionMessages.INVALID_VERIFICATION_CODE);
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiresAt(null);
        userRepository.save(user);
        logger.info("User {} verified successfully", user.getEmail());
    }

    @Override
    public void resendVerify(EmailVerificationRequest emailVerificationRequest) {
        logger.info("Resend verification request for email: {}", emailVerificationRequest.getEmail());
        User user = userRepository.findByEmail(emailVerificationRequest.getEmail());
        if (user == null) {
            logger.warn("Resend verification failed: User with email {} not found", emailVerificationRequest.getEmail());
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }

        if (user.isEnabled()) {
            logger.warn("Resend verification failed: User with email {} is already verified", emailVerificationRequest.getEmail());
            throw new UserException(ExceptionMessages.ACCOUNT_ALREADY_VERIFIED);
        }

        String newVerificationCode = generateVerificationCode();
        user.setVerificationCode(newVerificationCode);
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(1));

        sendVerificationEmail(user);
        userRepository.save(user);
        logger.info("New verification email sent to {}", user.getEmail());
    }

    private Authentication authenticate(String username, String password) {
        logger.info("Authenticating user with email: {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.warn("Authentication failed for user with email: {}", username);
            throw new UserException(ExceptionMessages.USER_NOT_FOUND_OR_PASSWORD_IS_WRONG);
        }

        logger.info("Authentication successful for user with email: {}", username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        logger.debug("Generated verification code: {}", code);
        return String.valueOf(code);
    }

    private void sendVerificationEmail(User user) {
        logger.info("Sending verification email to {}", user.getEmail());
        try {
            rabbitTemplate.convertAndSend(exchange, emailRoutingKey, user);
            logger.info("Verification email sent successfully to {}", user.getEmail());
        } catch (AmqpException e) {
            logger.error("Error occurred while sending verification email to {}", user.getEmail(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "E-posta gönderiminde bir hata oluştu.");
        }
    }

    private void sendPasswordResetEmail(User user) {
        logger.info("Sending password reset email to {}", user.getEmail());
        try {
            rabbitTemplate.convertAndSend(exchange, passwordRoutingKey, user);
            logger.info("Password reset email sent successfully to {}", user.getEmail());
        } catch (AmqpException e) {
            logger.error("Error occurred while sending password reset email to {}", user.getEmail(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "E-posta gönderiminde bir hata oluştu.");
        }
    }
}
