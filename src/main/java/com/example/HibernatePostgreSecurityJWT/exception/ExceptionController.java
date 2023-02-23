package com.example.HibernatePostgreSecurityJWT.exception;

import com.example.HibernatePostgreSecurityJWT.exception.customizations.BadCredentialsLoginFailed;
import com.example.HibernatePostgreSecurityJWT.exception.customizations.DataAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
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

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.CONFLICT.value());
        data.put("reason",exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(data);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExistsException(DataAlreadyExistsException exception){
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.CONFLICT.value());
        data.put("reason",exception.getReason());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(data);
    }

    @ExceptionHandler(BadCredentialsLoginFailed.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExistsException(BadCredentialsLoginFailed exception){
        //logger.warn(exception.getReason()+" intentando acceder");
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.NOT_ACCEPTABLE.value());
        data.put("reason","usuario o clave invalida");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(data);
    }


}
