package com.example.HibernatePostgreSecurityJWT.service.impl;

import com.example.HibernatePostgreSecurityJWT.dto.repository.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.RoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
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
public class UserServiceImpl implements UserDetailsService, UserService {


    RepositoryPersonalized repositoryPersonalized;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bcryptEncoder;

    public UserServiceImpl(RepositoryPersonalized repositoryPersonalized,
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
     * @param username String dado
     * @return user de la clase security.core
     * @throws UsernameNotFoundException expecion
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
     * @param user usuario de entitie
     * @return authorizaciones que posee el user
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
    public User saveUser(User user) {
// TODO verificar el user, documento, email: que sean unicos antes de almacenar, si no devolver una expecion
        //encriptar la contraseña
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        //guardar el usuario
        user.setId(repositoryPersonalized.UserID());
        return userRepository.save(user);

    }

    @Override
    public UserDto saveRoleByUser(User user, Role role) {
        //crea un UserRole para almacenarlo en la base de datos
        UserRole userRole = new UserRole(null,user,role);
        userRole.setId(repositoryPersonalized.UserRoleID());
        userRoleRepository.save(userRole);
        //genera un auxiliar para hacer un Json para devolver
        List<String> rolesAux = new ArrayList<>();
        rolesAux.add(role.getName());
        //generar el usuario con los roles asignados y devolver
        return new UserDto(user,rolesAux);
    }

    @Override
    public Role findRoleByrol(String role){
        return  repositoryPersonalized.findRoleByNameRol(role);
    }


    @Override
    public List<User> findAllUser() {
        return repositoryPersonalized.findAllUser();
    }
}
