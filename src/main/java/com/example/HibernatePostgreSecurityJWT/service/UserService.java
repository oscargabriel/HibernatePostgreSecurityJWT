package com.example.HibernatePostgreSecurityJWT.service;

import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.User;

public interface UserService {
    public UserDto save(User user);
}
