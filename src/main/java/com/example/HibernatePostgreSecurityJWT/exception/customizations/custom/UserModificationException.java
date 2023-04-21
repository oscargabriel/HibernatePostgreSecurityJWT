package com.example.HibernatePostgreSecurityJWT.exception.customizations.custom;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * exepcion si intenta hacer una midificacion sobre un usuario al cual no tiene permiso
 * si es usuario solo puede modificarse a si mismo
 * si es admin puede modificar cualquier usuario
 */

public class UserModificationException extends ResponseStatusException {
    public UserModificationException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
