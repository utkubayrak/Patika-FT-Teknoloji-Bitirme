package com.utkubayrak.jetbususerservice.dto.response;

import com.utkubayrak.jetbususerservice.model.enums.GenderEnum;
import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;
import lombok.Data;

@Data
public class RegisterResponse {
    private String fullName;
    private String email;
    private RoleEnum role;
    private GenderEnum gender;
    private String message;
}
