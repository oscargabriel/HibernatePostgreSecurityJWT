package com.example.HibernatePostgreSecurityJWT.security.component;

import com.example.HibernatePostgreSecurityJWT.exception.customizations.BadCredentialsLoginFailed;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException, BadCredentialsLoginFailed{

        Logger logger = LoggerFactory.getLogger(UnauthorizedEntryPoint.class);


        if(authException.getClass().getSimpleName().equalsIgnoreCase("BadCredentialsException")){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            Map<String, Object> data = new HashMap<>();
            data.put("timestamp", new Date());
            data.put("status",HttpStatus.FORBIDDEN.value());
            data.put("message", "usuario o clave invalida");

            OutputStream out = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, data);
            out.flush();
        }
        logger.warn("usuario no authenticado ingresar a lugar prohibido: "+request.getRequestURL().toString());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status",HttpStatus.FORBIDDEN.value());
        data.put("message", "acceso denegado");
        data.put("path", request.getRequestURL().toString());

        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, data);
        out.flush();
    }
}
