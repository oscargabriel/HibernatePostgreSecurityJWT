package com.example.HibernatePostgreSecurityJWT.service;

import com.example.HibernatePostgreSecurityJWT.dto.service.AuthToken;
import com.example.HibernatePostgreSecurityJWT.dto.controller.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.repository.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.User;

import java.util.List;

public interface UserService {

    public UserDto save(User user);

    public AuthToken authenticate(LoginUser loginUser);

    public List<User> findAllUser();
}
