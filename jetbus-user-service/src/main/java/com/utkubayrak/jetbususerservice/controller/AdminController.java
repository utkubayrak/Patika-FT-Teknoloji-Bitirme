package com.utkubayrak.jetbususerservice.controller;

import com.utkubayrak.jetbususerservice.dto.request.ChangeRoleRequest;
import com.utkubayrak.jetbususerservice.model.User;
import com.utkubayrak.jetbususerservice.model.enums.RoleEnum;
import com.utkubayrak.jetbususerservice.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final IAdminService adminService;

    @PutMapping("/update-role/{userId}")
    public ResponseEntity<User> changeRoleUser(@PathVariable Long userId, @RequestBody ChangeRoleRequest changeRoleRequest){

        User user = adminService.changeRoleUser(userId,changeRoleRequest);
        return ResponseEntity.ok(user);
    }
}
