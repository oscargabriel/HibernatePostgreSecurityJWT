package com.example.HibernatePostgreSecurityJWT.repsitory.dao.Impl;

import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorySqlImpl implements RepositoryPersonalized {

    @PersistenceContext//definir la variable para las consultas
    private EntityManager entityManager;



    @Override
    public List<User> findAllUser() {
        Query query = entityManager.createNativeQuery("SELECT * FROM users", User.class);
        List<User> users = query.getResultList();
        users.forEach(System.out::println);
        return users;
    }


    @Override
    public User findUserByUsername(String username) {
        /*Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery<User> query = session.createNativeQuery(
                "SELECT * FROM users u where u.username = :username",User.class);
        query.setParameter("username",username);
        User user = query.getSingleResultOrNull();
        session.close();
        return user;*/
        return null;
    }

    @Override
    public Role findRoleByNameRol(String name) {
        /*Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery<Object[]> query = session.createNativeQuery(
                "SELECT * FROM role r WHERE r.name = :name ",Object[].class);
        query.setParameter("name",name);
        System.out.println();
        Object[] o = query.getSingleResultOrNull();
        session.close();
        if(o.length==0){return null; }
        return new Role((Long) o[0], (String) o[2], (String)o[1]);*/
        return null;
    }

    @Override
    public List<String> findRolesByUsername(String username) {
        /*List<String> roles;
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
        return roles;*/
        return null;
    }



    @Override
    public User saveUser(User user) {
     /*   Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch (PersistenceException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return user;*/
        return null;
    }

}
