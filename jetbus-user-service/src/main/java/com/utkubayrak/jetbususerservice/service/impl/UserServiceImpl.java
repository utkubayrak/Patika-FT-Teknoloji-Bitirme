package com.utkubayrak.jetbususerservice.service.impl;

import com.utkubayrak.jetbususerservice.exception.ExceptionMessages;
import com.utkubayrak.jetbususerservice.exception.UserException;
import com.utkubayrak.jetbususerservice.model.User;
import com.utkubayrak.jetbususerservice.repository.UserRepository;
import com.utkubayrak.jetbususerservice.security.service.JwtProvider;
import com.utkubayrak.jetbususerservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        logger.info("Finding user by JWT token");
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        logger.info("User found by JWT token: {}", user.getEmail());
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        logger.info("Finding user by email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.error("User not found with email: {}", email);
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }
        logger.info("User found: {}", email);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        logger.info("Retrieving all users");
        List<User> users = userRepository.findAll();
        logger.info("Total users found: {}", users.size());
        return users;
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent()) {
            logger.error("User not found with ID: {}", id);
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
        logger.info("User deleted with ID: {}", id);
    }

    @Override
    public User updateUser(User userEntity) {
        logger.info("Updating user: {}", userEntity.getEmail());
        // Güncelleme işlemleri burada yapılabilir.
        logger.warn("Update method is not yet implemented");
        return null;
    }
}
