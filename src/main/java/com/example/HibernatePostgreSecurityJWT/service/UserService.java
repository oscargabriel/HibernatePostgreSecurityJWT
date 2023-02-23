package com.example.HibernatePostgreSecurityJWT.service;

import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;

import java.util.List;

public interface UserService {

    /**
     * guarda el usuario
     * @param user recibe los datos del usuario para su verifiacion y almacenar
     * @return regresa los datos del usuario
     */
    public User saveUser(User user);

    /**
     * asigna los roles a los usuarios en la base de datos
     * @param user recive un usuario completo
     * @param role recibe el rol que se va a asignar
     * @return
     */
    public UserDto saveRoleByUser(User user, Role role);

    /**
     * busca un rol dado el nombre del rol
     * @param role
     * @return
     */
    public Role findRoleByrol(String role);

    /**
     * muestra los usuarios almacenados en la base de datos
     * @return
     */
    public List<User> findAllUser();
}
