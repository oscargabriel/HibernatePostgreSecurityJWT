package com.example.HibernatePostgreSecurityJWT.repsitory.hibernate.Impl;

import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import com.example.HibernatePostgreSecurityJWT.repsitory.hibernate.RepositoryHibernate;
import com.example.HibernatePostgreSecurityJWT.util.HibernateUtil;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class RepositoryHibernateImpl implements RepositoryHibernate {

    @Override
    public User findUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery<User> query = session.createNativeQuery(
                "SELECT * FROM users u where u.username = :username",User.class);
        query.setParameter("username",username);
        User user = query.getSingleResultOrNull();
        session.close();
        return user;
    }

    @Override//error con al intentar recuperar el rol directamente, se uso object[] y luego cast
    public Role findRoleByNameRol(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery<Object[]> query = session.createNativeQuery(
                "SELECT * FROM role r WHERE r.name = :name ",Object[].class);
        query.setParameter("name",name);
        System.out.println();
        Object[] o = query.getSingleResultOrNull();
        session.close();
        if(o.length==0){return null; }
        return new Role((Long) o[0], (String) o[2], (String)o[1]);
    }

    @Override
    public List<String> findRolesByUsername(String username) {
        List<String> roles;
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery<String> query = session.createNativeQuery(
                "SELECT r.name " +
                        "FROM users u " +
                        "JOIN user_role ur ON u.id = ur.user_id " +
                        "JOIN role r ON r.id = ur.role_id " +
                        "WHERE u.username = :username",String.class);
        query.setParameter("username",username);

        roles = query.list();
        session.close();
        if(roles.size()==0){ return null; }
        return roles;
    }

    @Override
    public List<User> findAllUser() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = session.createNativeQuery("SELECT * FROM users",User.class).list();
        if(users.size()==0){ return null; }
        session.close();
        return users;
    }

    @Override
    public User saveUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return user;
    }

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(userRole);
            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return userRole;
    }
}
