package com.example.HibernatePostgreSecurityJWT.repsitory.hibernate.Impl;

import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.repsitory.hibernate.RepositoryHibernate;
import com.example.HibernatePostgreSecurityJWT.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class RepositoryHibernateImpl implements RepositoryHibernate {

    public RepositoryHibernateImpl() {
    }



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

    @Override
    public Role findRoleByNameRol(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery<Role> query = session.createNativeQuery(
                "SELECT * FROM role r WHERE r.name = :name",Role.class);
        query.setParameter("name",name);
        Role rol = query.getSingleResultOrNull();
        session.close();
        return rol;
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
}
