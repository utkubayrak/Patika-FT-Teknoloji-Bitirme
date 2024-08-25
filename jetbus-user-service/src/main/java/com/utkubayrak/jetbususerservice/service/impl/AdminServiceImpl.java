package com.utkubayrak.jetbususerservice.service.impl;

import com.utkubayrak.jetbususerservice.dto.request.ChangeRoleRequest;
import com.utkubayrak.jetbususerservice.exception.ExceptionMessages;
import com.utkubayrak.jetbususerservice.exception.UserException;
import com.utkubayrak.jetbususerservice.model.User;
import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;
import com.utkubayrak.jetbususerservice.repository.UserRepository;
import com.utkubayrak.jetbususerservice.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements IAdminService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public User changeRoleUser(Long userId, ChangeRoleRequest changeRoleRequest) {
        logger.debug("Creating change role request: {}", changeRoleRequest, userId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new UserException(ExceptionMessages.USER_NOT_FOUND);
        }
        User user = userOpt.get();
        user.setRole(changeRoleRequest.getRole());
        userRepository.save(user);
        logger.info("Change role: {}", user);
        return user;
    }
}
