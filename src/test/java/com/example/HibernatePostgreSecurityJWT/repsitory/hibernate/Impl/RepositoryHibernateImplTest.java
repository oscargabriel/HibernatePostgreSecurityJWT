package com.example.HibernatePostgreSecurityJWT.repsitory.hibernate.Impl;

import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.repsitory.hibernate.RepositoryHibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryHibernateImplTest {

    RepositoryHibernate repo;
    @BeforeEach
    void setUp() {
        repo = new RepositoryHibernateImpl();
    }

    @Test
    void findUserByUsername() {
        User user = repo.findUserByUsername("zaos");
        System.out.println(user.toString());
        assertNotEquals(null, user);
    }
    @Test
    void findUserByUsernameNull() {
        User user = repo.findUserByUsername("zaos111111111111111111111111111111111111");
        assertNull(user);
    }

    @Test
    void findRoleByName() {
        Role role = repo.findRoleByNameRol("USER");
        System.out.println(role.toString());
        assertNotEquals(null, role);
    }
    @Test
    void findRoleByNameNull() {
        Role role = repo.findRoleByNameRol("USER111111111111111111111111111");
        assertNull(role);

    }

    @Test
    void findRolesByUsername() {
        List<String> roles = repo.findRolesByUsername("zaos1");
        roles.forEach(System.out::println);
        assertNotEquals(null, roles);
    }

    @Test
    void findRolesByUsernameNull() {
        List<String> roles = repo.findRolesByUsername("zaos1111111111111");
        assertNull(roles);
    }
}