package com.utkubayrak.jetbususerservice.service;

import com.utkubayrak.jetbususerservice.model.User;

import java.util.List;

public interface IUserService {
    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public List<User> getAllUser();
    public void deleteUser(Long id);
    public User updateUser(User userEntity);
}
