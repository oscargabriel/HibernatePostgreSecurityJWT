package com.example.HibernatePostgreSecurityJWT.exception.customizations;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * execpcion para almencar datos por "email, username o document" existente
 */
public class DataAlreadyExistsException extends ResponseStatusException {

    public DataAlreadyExistsException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
