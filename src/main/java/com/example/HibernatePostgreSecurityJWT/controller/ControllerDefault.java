package com.example.HibernatePostgreSecurityJWT.controller;

import com.example.HibernatePostgreSecurityJWT.dto.controller.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.repository.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * interfaz para los metodos que recibe mediante http
 */
public interface ControllerDefault {


    /**
     * funciones de prueba
     * @return
     */
    public ResponseEntity<String> hola();
    public ResponseEntity<String> holaauthenticate();
    public ResponseEntity<String> userPing();
    public ResponseEntity<String> employeePing();
    public ResponseEntity<String> adminPing();


    /**
     * registrar usuario, recibe todos lo datos del usuario
     * @param user
     * @return el usuario creado con su rol asigando por defecto
     */
    public ResponseEntity<UserDto> register(@RequestBody User user);

    /**
     * muestra todos los usuarios en la base de datos
     * @return
     */
    public ResponseEntity<List<User>> findAllUser();

    /**
     * autenticar usuario
     * @param loginUser username y password
     * @return token de autenticacion para el acceso
     */
    public ResponseEntity<?> authenticate(LoginUser loginUser);


    }
