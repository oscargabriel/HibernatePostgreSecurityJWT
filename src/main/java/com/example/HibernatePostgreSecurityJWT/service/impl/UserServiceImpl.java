package com.example.HibernatePostgreSecurityJWT.service.impl;

import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.RoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {


    RepositoryPersonalized repositoryPersonalized;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(RepositoryPersonalized repositoryPersonalized, RoleRepository roleRepository, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.repositoryPersonalized = repositoryPersonalized;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override// TODO verificar el user, documento, email: que sean unicos antes de almacenar, si no devolver una expecion
    public UserDto save(User user) {

        //encriptar la contraseña
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        //guardar el usuario
        user.setId(repositoryPersonalized.UserID());
        User userAux = userRepository.save(user);
        System.out.println("guardo el usuario "+ userAux);
        //busca el rol por defecto para asignarlo

        Role roles = repositoryPersonalized.findRoleByNameRol("USER");
        System.out.println("genero el rol "+roles);
        //crea un UserRole para almacenarlo en la base de datos
        UserRole userRole = new UserRole(null,userAux,roles);
        //userRole.setId(repositoryPersonalized.generarID("user_role"));
        userRole.setId(repositoryPersonalized.UserRole());
        UserRole us = userRoleRepository.save(userRole);
        System.out.println("Guardo el UserRole "+us);
        //genera un auxiliar para hacer un Json para devolver
        List<String> rolesAux = new ArrayList<>();
        rolesAux.add("USER");
        //generar el usuario con los roles asignados y devolver

        //  HERROR DE PERSISTENCIA INVESTIGAR
        //userAux y roles Aux
        UserDto userDto = new UserDto(userAux,rolesAux);
        return userDto;
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = repositoryPersonalized.findAllUser();
        return users;
    }
}
