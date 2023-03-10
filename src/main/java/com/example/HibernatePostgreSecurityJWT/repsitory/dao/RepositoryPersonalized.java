package com.example.HibernatePostgreSecurityJWT.repsitory.dao;

import com.example.HibernatePostgreSecurityJWT.dto.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;

import java.util.List;

/**
 * llamada personalizada a la base de datos para realizar consultas
 */
public interface RepositoryPersonalized {

    //generationtype.identity no devuelve el siguiente id, se realizao de forma manual
    Long UserID();
    Long RoleID();
    Long UserRoleID();


    //
    Boolean existByEmail(String email);

    Boolean existByUsername(String username);

    Boolean existByDocument(String document);

    /**
     * recuperar un usuario apartir del username
     * @param username que se va a buscar
     * @return el usuario
     */
    User findUserByUsername(String username);

    /**
     * recuperar un rol apartir del nombre
     * @param name nombre del rol
     * @return el rol
     */
    Role findRoleByNameRol(String name);

    /**
     * recupera los roles que tiene asignado un usuario apartir del username
     * @param username del usuario a buscar
     * @return roles asignados
     */
    List<String> findRolesByUsername(String username);

    /**
     * devuelve tosos los usuarios de la base de datos
     * @return
     */
    List<User> findAllUser();

    /**
     * busca el id de los roles que tengo el usuario en la tabla asociativa con roles
     * @param id
     * @return
     */
    List<Long> findIdUserRoleByUserId(Long id);

    /**
     * busca un usuario por su id
     * @param id
     * @return
     */
    User findUserById(Long id);
}
