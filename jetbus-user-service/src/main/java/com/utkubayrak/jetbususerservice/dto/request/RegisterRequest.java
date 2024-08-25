package com.utkubayrak.jetbususerservice.dto.request;

import com.utkubayrak.jetbususerservice.model.enums.GenderEnum;
import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private boolean isCorporate;
    private GenderEnum gender;
}
