package com.example.HibernatePostgreSecurityJWT.repsitory.dao.Impl;

import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositorySqlImplTest {

    RepositoryPersonalized repositoryPersonalized;

    @BeforeEach
    void setUp() {
        repositoryPersonalized = new RepositorySqlImpl();
    }


    @Test
    void findAllUser() {

    }

    @Test
    void findUserByUsername() {
        final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        System.out.println(bcryptEncoder.encode("zaos1"));
    }

    @Test
    void findRoleByNameRol() {
    }

    @Test
    void findRolesByUsername() {
    }
}