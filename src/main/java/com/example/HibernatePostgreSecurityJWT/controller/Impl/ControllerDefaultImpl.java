package com.example.HibernatePostgreSecurityJWT.controller.Impl;

import com.example.HibernatePostgreSecurityJWT.controller.ControllerDefault;
import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerDefaultImpl implements ControllerDefault {



    private final UserService userService;

    private RepositoryPersonalized repositoryPersonalized;

    public ControllerDefaultImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/hola")
    public ResponseEntity<String> hola(){
        return ResponseEntity.ok("hola");
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user){
        // TODO: gestionar cuando sea positiva y excepcion
        return ResponseEntity.ok().body(userService.save(user));

    }

    @Override
    @GetMapping("/findAllUser")
    public ResponseEntity<List<User>> findAllUser() {
        try {
            List<User> users = userService.findAllUser();
            return ResponseEntity.ok().body(users);
        }catch (Exception e){
            System.out.println(e.getMessage() +" | "+ e.getCause());
        }
        return ResponseEntity.badRequest().build();


    }
}
