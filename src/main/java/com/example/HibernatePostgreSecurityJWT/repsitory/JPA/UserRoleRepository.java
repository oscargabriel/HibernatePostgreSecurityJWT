package com.example.HibernatePostgreSecurityJWT.repsitory.JPA;

import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole,Long> {
}
