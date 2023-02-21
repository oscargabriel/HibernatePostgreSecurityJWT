package com.example.HibernatePostgreSecurityJWT.service.impl;

import com.example.HibernatePostgreSecurityJWT.dto.service.AuthToken;
import com.example.HibernatePostgreSecurityJWT.dto.controller.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.repository.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.RoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import com.example.HibernatePostgreSecurityJWT.security.jwt.TokenProvider;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserServiceImpl implements UserDetailsService, UserService {

    /*@Autowired
    private AuthenticationManager authenticationManager;*/

    @Autowired
    private TokenProvider jwtTokenUtil;

    RepositoryPersonalized repositoryPersonalized;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();


    public UserServiceImpl(RepositoryPersonalized repositoryPersonalized,
                           RoleRepository roleRepository,
                           UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           TokenProvider jwtTokenUtil//,
                           //AuthenticationManager authenticationManager
    ) {
        //this.authenticationManager = authenticationManager;
        this.repositoryPersonalized = repositoryPersonalized;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repositoryPersonalized.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Usuario o Clave Invalida");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<String> roles = repositoryPersonalized.findRolesByUsername(user.getUsername());
        roles.forEach( rol -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+rol));
        });
        return authorities;
    }

    @Override
    public UserDto save(User user) {
// TODO verificar el user, documento, email: que sean unicos antes de almacenar, si no devolver una expecion

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
        UserDto userDto = new UserDto(userAux,rolesAux);
        return userDto;
    }

    @Override
    public AuthToken authenticate(LoginUser loginUser) {
        System.out.println(loginUser.getUsername());
        System.out.println(loginUser.getPassword());

        /*SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);*/
        return new AuthToken("token");
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = repositoryPersonalized.findAllUser();
        return users;
    }


}
