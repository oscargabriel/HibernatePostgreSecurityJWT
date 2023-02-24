package com.example.HibernatePostgreSecurityJWT.exception.customizations.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserModificationException extends ResponseStatusException {
    public UserModificationException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
