package com.example.HibernatePostgreSecurityJWT.service.impl;

import com.example.HibernatePostgreSecurityJWT.dto.repository.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.RoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import com.example.HibernatePostgreSecurityJWT.service.UserServicea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceaImpl implements UserDetailsService, UserServicea {

    @Autowired
    RepositoryPersonalized repositoryPersonalized;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public UserServiceaImpl(RepositoryPersonalized repositoryPersonalized,
                            RoleRepository roleRepository,
                            UserRepository userRepository,
                            UserRoleRepository userRoleRepository,
                            BCryptPasswordEncoder bcryptEncoder) {
        this.repositoryPersonalized = repositoryPersonalized;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    /**
     * implementada desde UserDetailService para verificar que el usuario y la clave sean validas
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //verifica que el username exista si no devuelve una expecion
        User user = repositoryPersonalized.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuario o Clave Invalida");
        }
        //devueelve el usuario y las autorizaciones que tiene el usuario
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),getAuthority(user));
    }

    /**
     * se encarga de buscar las authorizaciones que tenga el usuario para la funcion previa
     * @param user
     * @return
     */
    private Set<SimpleGrantedAuthority> getAuthority(User user){
        //crea una variable para almacenar las authorizaciones
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        //crea una lista de las authorizaciones
        List<String> roles = repositoryPersonalized.findRolesByUsername(user.getUsername());
        //los asigna y retorna
        roles.forEach( rol -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+rol));
        });
        return authorities;
    }


    @Override
    public UserDto saveUser(User user) {
// TODO verificar el user, documento, email: que sean unicos antes de almacenar, si no devolver una expecion
        //encriptar la contraseña
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        //guardar el usuario
        user.setId(repositoryPersonalized.UserID());
        User userAux = userRepository.save(user);
        //busca el rol por defecto para asignarlo
        Role role = findRoleByrol("USER");
        return saveRoleByUser(userAux, role);
    }

    @Override
    public UserDto saveRoleByUser(User user, Role role) {
        //crea un UserRole para almacenarlo en la base de datos
        UserRole userRole = new UserRole(null,user,role);
        userRole.setId(repositoryPersonalized.UserRoleID());
        UserRole us = userRoleRepository.save(userRole);
        //genera un auxiliar para hacer un Json para devolver
        List<String> rolesAux = new ArrayList<>();
        rolesAux.add("USER");
        //generar el usuario con los roles asignados y devolver
        return new UserDto(user,rolesAux);
    }

    @Override
    public Role findRoleByrol(String role){
        return  repositoryPersonalized.findRoleByNameRol(role);
    }


    @Override
    public List<User> findAllUser() {
        List<User> users = repositoryPersonalized.findAllUser();
        return users;
    }
}
