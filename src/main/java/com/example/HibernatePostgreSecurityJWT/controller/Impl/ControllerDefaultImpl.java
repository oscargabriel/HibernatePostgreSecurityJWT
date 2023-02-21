package com.example.HibernatePostgreSecurityJWT.controller.Impl;

import com.example.HibernatePostgreSecurityJWT.controller.ControllerDefault;
import com.example.HibernatePostgreSecurityJWT.dto.controller.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.repository.UserDto;
import com.example.HibernatePostgreSecurityJWT.dto.service.AuthToken;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.security.jwt.TokenProvider;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ControllerDefaultImpl implements ControllerDefault {


    @Autowired
    private final UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final TokenProvider jwtTokenUtil;

    public ControllerDefaultImpl(AuthenticationManager authenticationManager,
                                 UserService userService,
                                 TokenProvider jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    @GetMapping("/hola")
    public ResponseEntity<String> hola(){
        System.out.println("saludo");
        return ResponseEntity.ok("hola");
    }

    @Override
    @GetMapping("/hola_authenticate")
    public ResponseEntity<String> holaauthenticate() {
        System.out.println("authenticate");
        return ResponseEntity.ok("hola authenticate");
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user){
        System.out.println("Guardando");
        // TODO: gestionar cuando sea positiva y excepcion
        return ResponseEntity.ok().body(userService.save(user));

    }

    @Override
    @GetMapping("/findAllUser")
    public ResponseEntity<List<User>> findAllUser() {
        System.out.println("mostrando todos los usuarios");
        List<User> users = userService.findAllUser();
        return ResponseEntity.ok().body(users);
    }

    @Override
    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser){
        System.out.println("authenticando");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }
}
