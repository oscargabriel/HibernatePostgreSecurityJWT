package com.example.HibernatePostgreSecurityJWT.service;

import com.example.HibernatePostgreSecurityJWT.dto.repository.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.User;

import java.util.List;

public interface UserServicea {

    public UserDto save(User user);

    public List<User> findAllUser();
}
