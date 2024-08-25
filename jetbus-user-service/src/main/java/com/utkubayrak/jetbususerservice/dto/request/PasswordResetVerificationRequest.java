package com.utkubayrak.jetbususerservice.dto.request;

import lombok.Data;

@Data
public class PasswordResetVerificationRequest {
    private String email;
    private String resetCode;
}
