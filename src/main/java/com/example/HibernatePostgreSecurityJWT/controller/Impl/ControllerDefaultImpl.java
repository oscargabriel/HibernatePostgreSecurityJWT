package com.example.HibernatePostgreSecurityJWT.controller.Impl;

import com.example.HibernatePostgreSecurityJWT.controller.ControllerDefault;
import com.example.HibernatePostgreSecurityJWT.dto.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.dto.AuthToken;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.security.jwt.TokenProvider;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * recibe mensajes desde http GET, POST, PUT y DELETE
 * usa los roles para discriminar los accesos
 *
 * tiene una copia de userService para para que se encargeue de procesar el contenido de los mensajes
 * Authentication manager y tokenproviden para la autenticacion y generacion del token (si se ejecuta en service se genera un conflicto de llamda)
 *
 */
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
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello_user")
    public ResponseEntity<String> userPing() {
        return ResponseEntity.ok("hola USER");
    }
    @Override
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/hello_employee")
    public ResponseEntity<String> employeePing() {
        return ResponseEntity.ok("hola EMPLOYEE");
    }
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello_admin")
    public ResponseEntity<String> adminPing(){
        return ResponseEntity.ok("hola ADMIN");
    }

    @Override
    @PreAuthorize("hasAnyRole('USER','EMPLOYEE')")
    @GetMapping("/hello_user_employee")
    public ResponseEntity<String> useremployeeePing() {
        return ResponseEntity.ok("hola USER_EMPLOYEE");
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user){
        System.out.println("Guardando");
        //guarda el usuario
            User userAux = userService.saveUser(user);
        //busca el rol por defecto a asignar
        Role role = userService.findRoleByrol("USER");
        //asigna el rol y return
        // TODO: gestionar cuando sea positiva y excepcion
        return ResponseEntity.ok().body(userService.saveRoleByUser(userAux,role));

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
    public ResponseEntity<?> authenticate(@RequestBody LoginUser loginUser){
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
