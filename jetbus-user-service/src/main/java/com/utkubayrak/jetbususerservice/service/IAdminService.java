package com.utkubayrak.jetbususerservice.service;

import com.utkubayrak.jetbususerservice.dto.request.ChangeRoleRequest;
import com.utkubayrak.jetbususerservice.model.User;
import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;

public interface IAdminService {

    public User changeRoleUser(Long userId, ChangeRoleRequest changeRoleRequest);

}
