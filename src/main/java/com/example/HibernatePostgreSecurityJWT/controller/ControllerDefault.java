package com.example.HibernatePostgreSecurityJWT.controller;

import com.example.HibernatePostgreSecurityJWT.dto.controller.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.repository.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ControllerDefault {


    public ResponseEntity<String> hola();

    public ResponseEntity<String> holaauthenticate();

    public ResponseEntity<UserDto> register(@RequestBody User user);

    public ResponseEntity<List<User>> findAllUser();

    public ResponseEntity<?> generateToken(LoginUser loginUser);

    }
