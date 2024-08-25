package com.utkubayrak.jetbusemailservice.service;

import com.utkubayrak.jetbusemailservice.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void handleUserForEmail(User user) throws MessagingException {
        log.info("Received user data for email verification: {}", user.getEmail());
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(htmlMessage, true);

            emailSender.send(message);
            log.info("Verification email sent to {}", user.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send verification email to {}: {}", user.getEmail(), e.getMessage());
            throw e;
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.password.name}")
    public void handlePasswordEmail(User user) throws MessagingException {
        log.info("Received user data for password reset: {}", user.getEmail());
        String subject = "Password Reset";
        String resetCode = "RESET CODE " + user.getResetCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Password Reset Code:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + resetCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(htmlMessage, true);

            emailSender.send(message);
            log.info("Password reset email sent to {}", user.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send password reset email to {}: {}", user.getEmail(), e.getMessage());
            throw e;
        }
    }
}
