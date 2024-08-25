package com.utkubayrak.jetbususerservice.controller;

import com.utkubayrak.jetbususerservice.dto.request.LoginRequest;
import com.utkubayrak.jetbususerservice.dto.request.RegisterRequest;
import com.utkubayrak.jetbususerservice.dto.request.EmailVerificationRequest;
import com.utkubayrak.jetbususerservice.dto.request.VerifyUserRequest;
import com.utkubayrak.jetbususerservice.dto.request.PasswordResetRequest;
import com.utkubayrak.jetbususerservice.dto.response.LoginResponse;
import com.utkubayrak.jetbususerservice.dto.response.RegisterResponse;
import com.utkubayrak.jetbususerservice.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final IAuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            System.out.println(registerRequest.isCorporate());
            RegisterResponse registeredUser = authService.registerUser(registerRequest);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.loginUser(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error logging in: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserRequest verifyUserRequest) {
        try {
            authService.verifyUser(verifyUserRequest);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Verification failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }

    @PostMapping("/resend-verify")
    public ResponseEntity<?> resendVerify(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        try {
            authService.resendVerify(emailVerificationRequest);
            return ResponseEntity.ok("Verification code resent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error resending verification code: " + e.getMessage());
        }
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody EmailVerificationRequest emailVerificationRequest) {
        try {
            authService.initiatePasswordReset(emailVerificationRequest);
            return ResponseEntity.ok("Password reset code sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error sending password reset code: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        try {
            authService.resetPassword(passwordResetRequest);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error resetting password: " + e.getMessage());
        }
    }
}
