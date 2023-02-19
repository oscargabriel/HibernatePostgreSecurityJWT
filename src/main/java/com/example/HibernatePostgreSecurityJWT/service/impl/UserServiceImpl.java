package com.example.HibernatePostgreSecurityJWT.service.impl;

import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.RoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.hibernate.Impl.RepositoryHibernateImpl;
import com.example.HibernatePostgreSecurityJWT.repsitory.hibernate.RepositoryHibernate;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    //repositorios
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    RepositoryHibernate repositoryHibernate = new RepositoryHibernateImpl();

    //@Autowired
    private BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    @Override// TODO verificar el user, documento, email: que sean unicos antes de almacenar, si no devolver una expecion
    public UserDto save(User user) {

        //encriptar la contraseña
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        //guardar el usuario
        User userAux = userRepository.save(user);
        System.out.println("Usuario creado: "+userAux);
        //busca el rol por defecto para asignarlo
        Role roles = repositoryHibernate.findRoleByNameRol("USER");
        System.out.println("Rol creado: "+roles);
        //crea un UserRole para almacenarlo en la base de datos
        UserRole userRole = new UserRole(userAux,roles);
        System.out.println("UsuarioRol creado: "+userRole);
        userRoleRepository.save(userRole);
        //genera un auxiliar para hacer un Json para devolver fines demostrativos
        List<String> rolesAux = new ArrayList<>();
        rolesAux.add("USER");
        //generar el usuario con los roles asignados y devolver
        // TODO HERROR DE PERSISTENCIA INVESTIGAR
        UserDto userDto = new UserDto(userAux,rolesAux);
        //System.out.println(userDto.getName()+" "+userDto.getRole().size());
        return userDto;
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = repositoryHibernate.findAllUser();
        return users;
    }
}
