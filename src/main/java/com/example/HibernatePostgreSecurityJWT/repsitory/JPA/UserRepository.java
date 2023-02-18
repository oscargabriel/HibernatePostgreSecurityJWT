package com.example.HibernatePostgreSecurityJWT.repsitory.JPA;

import com.example.HibernatePostgreSecurityJWT.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
}
