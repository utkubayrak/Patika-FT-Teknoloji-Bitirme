package com.utkubayrak.jetbususerservice.dto.request;

import lombok.Data;

@Data
public class PasswordResetRequest {

    private String email;
    private String newPassword;
    private String resetCode;

}
