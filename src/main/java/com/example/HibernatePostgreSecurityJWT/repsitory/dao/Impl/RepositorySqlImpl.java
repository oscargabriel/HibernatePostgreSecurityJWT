package com.example.HibernatePostgreSecurityJWT.repsitory.dao.Impl;

import com.example.HibernatePostgreSecurityJWT.dto.LoginUser;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * implementacion de las funcones mediante llamdas sql
 */
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
    public Long RoleID() {
        Query query = entityManager.createNativeQuery(
                "SELECT MAX(id) FROM role", Long.class);
        return ((Long)query.getResultList().get(0)+1L);
    }
    @Override
    public Long UserRoleID() {
        Query query = entityManager.createNativeQuery(
                "SELECT MAX(id) FROM user_role", Long.class);
        return ((Long)query.getResultList().get(0)+1L);
    }
    //================================================================================


    @Override
    public Boolean existByEmail(String email) {
        //consulta sql
        Query query = entityManager.createNativeQuery(
                "SELECT u.email FROM users u WHERE u.email = :email", String.class);
        //indica cual es la condicion
        query.setParameter("email",email);
        //genera la lista
        List<String> exist = query.getResultList();
        //valida que no este vacia

        if (exist.size()==0) {return false;}
        return true;
    }

    @Override
    public Boolean existByUsername(String username) {
        //consulta sql
        Query query = entityManager.createNativeQuery(
                "SELECT u.username FROM users u WHERE u.username = :username", String.class);
        //indica cual es la condicion
        query.setParameter("username",username);
        //genera la lista
        List<String> exist = query.getResultList();
        //valida que no este vacia
        if (exist.size()==0) {return false;}
        return true;
    }

    @Override
    public Boolean existByDocument(String document) {
        //consulta sql
        Query query = entityManager.createNativeQuery(
                "SELECT u.document FROM users u WHERE u.document = :document", String.class);
        //indica cual es la condicion
        query.setParameter("document",document);
        //genera la lista
        List<String> exist = query.getResultList();
        //valida que no este vacia
        if (exist.size()==0) {return false;}
        return true;
    }
    //================================================================================
    @Override
    public List<User> findAllUser() {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM users", User.class);
        List<User> users = query.getResultList();
        //si no encuentra nada devuelve un null
        if (users.size()==0) return null;
        //devuelve la lista de usuarios
        return users;
    }

    @Override
    public User findUserByUsername(String username) {
        //consulta query
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM users u WHERE u.username = :username", User.class);
        //indica el parametro
        query.setParameter("username",username);
        //genera una lista resultante
        List<User> users = query.getResultList();
        //si no encuentra nada devuelve un null
        if (users.size()==0) return null;
        //devuelve solo el primer valor
        return users.get(0);
    }

    @Override
    public Role findRoleByNameRol(String name) {
        //consulta sql
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM role r WHERE r.name = :name", Role.class);
        //indica cual es la condicion
        query.setParameter("name",name);
        //genera la lista
        List<Role> role = query.getResultList();
        //valida que no este vacia
        if (role.size()==0) return null;
        //devuelve el primer valor
        return role.get(0);
    }



    @Override
    public List<String> findRolesByUsername(String username) {
        //consulta sql
        Query query = entityManager.createNativeQuery(
                "SELECT r.name " +
                        "FROM users u " +
                        "JOIN user_role ur ON u.id = ur.user_id " +
                        "JOIN role r ON r.id = ur.role_id " +
                        "WHERE u.username = :username",String.class);
        //valida la condicion
        query.setParameter("username",username);
        //genera una lista
        List<String> roles = query.getResultList();
        //verifica que no este vacia
        if (roles.size()==0) return null;
        //devuelve la lista
        return roles;
    }

    @Override
    public List<Long> findIdUserRoleByUserId(Long id) {
        //consulta sql
        Query query = entityManager.createNativeQuery(
                "select ur.id from user_role ur where ur.user_id = :id",Long.class);
        //valida la condicion
        query.setParameter("id",id);
        //genera una lista
        List<Long> ids = query.getResultList();
        //verifica que no este vacia
        if (ids.size()==0) return null;
        //devuelve la lista
        return ids;
    }

    @Override
    public User findUserById(Long id) {
        //consulta sql
        Query query = entityManager.createNativeQuery(
                "select * from users u where u.id = :id",User.class);
        //valida la condicion
        query.setParameter("id",id);
        //genera una lista
        List<User> user = query.getResultList();
        //verifica que no este vacia
        if (user.size()==0) return null;
        //devuelve la lista
        return user.get(0);
    }
}
