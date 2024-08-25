package com.utkubayrak.jetbusservice.client.user;

import com.utkubayrak.jetbusservice.client.user.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "user-service", url = "localhost:8080/api/users/profile")
public interface UserClient {

    @GetMapping()
    public ResponseEntity<UserResponse> getUserByJwt(@RequestHeader("Authorization") String jwt);
}
