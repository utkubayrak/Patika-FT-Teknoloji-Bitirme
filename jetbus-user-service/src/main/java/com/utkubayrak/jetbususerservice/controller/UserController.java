package com.utkubayrak.jetbususerservice.controller;

import com.utkubayrak.jetbususerservice.model.User;
import com.utkubayrak.jetbususerservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;



    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        userService.deleteUser(user.getId());
        return ResponseEntity.ok().body("Hesabınız silinmiştir.");
    }
}
