package com.example.HibernatePostgreSecurityJWT.exception.customizations;


import com.example.HibernatePostgreSecurityJWT.exception.customizations.custom.DataAlreadyExistsException;
import com.example.HibernatePostgreSecurityJWT.exception.customizations.custom.UserModificationException;
import com.example.HibernatePostgreSecurityJWT.exception.customizations.custom.UserToDeleteNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.InvalidApplicationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * gestionar las expeciones que se envian en respuesta el front
 */
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ExceptionController.class);


    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.NOT_FOUND.value());
        data.put("reason",exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    /**
     * exepcion cuando hay argumentos ilegales
     * @param exception
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.CONFLICT.value());
        data.put("reason",exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(data);
    }

    /**
     * exepcion cuando se intenta registrar usuario con credenciales duplicadas
     * @param exception
     * @return
     */
    @ExceptionHandler(DataAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExistsException(DataAlreadyExistsException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.CONFLICT.value());
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(data);
    }

    /**
     * exepcion cuando no se pudo autenticar el usuario por usuario o clave invalida
     * @param exception
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.FORBIDDEN.value());
        data.put("reason",exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(data);
    }

    @ExceptionHandler(UserToDeleteNotFound.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(UserToDeleteNotFound exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.EXPECTATION_FAILED.value());
        data.put("reason",exception.getReason());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(data);
    }

    @ExceptionHandler(UserModificationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(UserModificationException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.EXPECTATION_FAILED.value());
        data.put("reason",exception.getReason());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(data);
    }

    @ExceptionHandler(InvalidApplicationException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(InvalidApplicationException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.PRECONDITION_FAILED.value());
        data.put("reason",exception.getMessage());
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(data);
    }
}
