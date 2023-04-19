package com.example.HibernatePostgreSecurityJWT.repsitory.dao.Impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RepositorySqlImplTest {

    @Autowired
    RepositorySqlImpl repositorySql;

    @AfterEach
    void tearDown() {
    }

    @Test
    void notExistByEmail() {
        String email = "adasdasdasd";
        Boolean result = repositorySql.existByEmail(email);
        assertEquals(false, result);
    }
    @Test
    void ExistByEmail() {
        String email = "zaos@gmail.com";
        Boolean result = repositorySql.existByEmail(email);
        assertEquals(true, result);
    }
    @Test
    void existByUsername() {
    }

    @Test
    void existByDocument() {
    }

    @Test
    void findAllUser() {
    }

    @Test
    void findUserByUsername() {
    }

    @Test
    void findRoleByNameRol() {
    }

    @Test
    void findRolesByUsername() {
    }

    @Test
    void findIdUserRoleByUserId() {
    }

    @Test
    void findUserById() {
    }
}