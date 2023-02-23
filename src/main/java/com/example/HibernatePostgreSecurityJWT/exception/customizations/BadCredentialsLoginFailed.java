package com.example.HibernatePostgreSecurityJWT.exception.customizations;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class BadCredentialsLoginFailed extends ResponseStatusException {
    public BadCredentialsLoginFailed(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
