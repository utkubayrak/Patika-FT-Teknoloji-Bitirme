package com.utkubayrak.jetbususerservice.dto.request;

import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;
import lombok.Data;

@Data
public class ChangeRoleRequest {
    private RoleEnum role;
}
