package com.example.HibernatePostgreSecurityJWT.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerDefault {

    public ControllerDefault() {
    }

    @GetMapping("/hola")
    public ResponseEntity<String> hola(){
        return ResponseEntity.ok("hola");
    }

}
