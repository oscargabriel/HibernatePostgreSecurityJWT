package com.example.HibernatePostgreSecurityJWT.controller;

import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRoleRepository;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerDefault {


    private final UserService userService;




    public ControllerDefault(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hola")
    public ResponseEntity<String> hola(){
        return ResponseEntity.ok("hola");
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user){
        // TODO: gestionar cuando sea positiva y exepcion
        return ResponseEntity.ok().body(userService.save(user));
    }
}
