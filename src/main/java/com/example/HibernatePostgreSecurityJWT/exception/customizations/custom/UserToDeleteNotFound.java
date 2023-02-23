package com.example.HibernatePostgreSecurityJWT.exception.customizations.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UserToDeleteNotFound extends ResponseStatusException {

    public UserToDeleteNotFound(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
