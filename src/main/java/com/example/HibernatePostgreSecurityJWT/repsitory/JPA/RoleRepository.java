package com.example.HibernatePostgreSecurityJWT.repsitory.JPA;

import com.example.HibernatePostgreSecurityJWT.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>{
}
