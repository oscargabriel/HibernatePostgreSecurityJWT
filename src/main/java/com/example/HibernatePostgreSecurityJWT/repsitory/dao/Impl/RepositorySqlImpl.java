package com.example.HibernatePostgreSecurityJWT.repsitory.dao.Impl;

import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorySqlImpl implements RepositoryPersonalized {

    @PersistenceContext//definir la variable para las consultas
    private EntityManager entityManager;

    //================================================================================
    @Override
    public Long UserID() {
        Query query = entityManager.createNativeQuery(
                "SELECT MAX(id) FROM users", Long.class);
        return ((Long)query.getResultList().get(0)+1L);
    }

    @Override
    public Long Role() {
        Query query = entityManager.createNativeQuery(
                "SELECT MAX(id) FROM role", Long.class);
        return ((Long)query.getResultList().get(0)+1L);
    }

    @Override
    public Long UserRole() {
        Query query = entityManager.createNativeQuery(
                "SELECT MAX(id) FROM user_role", Long.class);
        return ((Long)query.getResultList().get(0)+1L);
    }
    //================================================================================

    @Override
    public List<User> findAllUser() {
        System.out.println("Repository mostrando todos los usuarios");
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM users", User.class);
        List<User> users = query.getResultList();
        if (users.size()==0) return null;
        System.out.println(users);
        return users;
    }


    @Override
    public User findUserByUsername(String username) {
        System.out.println("Repository mostrando un usuario por su username");
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM users u WHERE u.username = :username", User.class);
        query.setParameter("username",username);
        List<User> users = query.getResultList();
        if (users.size()==0) return null;
        System.out.println(users.get(0));
        return users.get(0);
    }

    @Override
    public Role findRoleByNameRol(String name) {
        System.out.println("Repository mostrando un rol por su name");
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM role r WHERE r.name = :name", Role.class);
        query.setParameter("name",name);
        List<Role> role = query.getResultList();
        if (role.size()==0) return null;
        System.out.println(role.get(0));
        return role.get(0);
    }

    @Override
    public List<String> findRolesByUsername(String username) {
        System.out.println("Repository mostrando los roles de un usuario por su username");
        Query query = entityManager.createNativeQuery(
                "SELECT r.name " +
                        "FROM users u " +
                        "JOIN user_role ur ON u.id = ur.user_id " +
                        "JOIN role r ON r.id = ur.role_id " +
                        "WHERE u.username = :username",String.class);
        query.setParameter("username",username);
        List<String> roles = query.getResultList();
        if (roles.size()==0) return null;
        return roles;
    }
}
