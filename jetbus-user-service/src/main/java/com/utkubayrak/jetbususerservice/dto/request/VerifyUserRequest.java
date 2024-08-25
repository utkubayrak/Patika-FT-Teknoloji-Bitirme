package com.utkubayrak.jetbususerservice.dto.request;

import lombok.Data;

@Data
public class VerifyUserRequest {
    private String email;
    private String verificationCode;
}
