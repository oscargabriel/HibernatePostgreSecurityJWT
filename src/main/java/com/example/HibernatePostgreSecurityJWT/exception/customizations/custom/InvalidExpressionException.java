package com.example.HibernatePostgreSecurityJWT.exception.customizations.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * exepcion para expresion regular invalida
 */
public class InvalidExpressionException extends ResponseStatusException {

    public InvalidExpressionException(HttpStatusCode status,String reason) {
        super(status,reason);
    }
}
