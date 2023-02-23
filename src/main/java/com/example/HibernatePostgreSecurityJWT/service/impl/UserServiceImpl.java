package com.example.HibernatePostgreSecurityJWT.service.impl;

import com.example.HibernatePostgreSecurityJWT.dto.AuthToken;
import com.example.HibernatePostgreSecurityJWT.dto.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import com.example.HibernatePostgreSecurityJWT.exception.customizations.custom.DataAlreadyExistsException;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.RoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import com.example.HibernatePostgreSecurityJWT.security.jwt.TokenProvider;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private AuthenticationManager authenticationManager;

    private final TokenProvider jwtTokenUtil;
    RepositoryPersonalized repositoryPersonalized;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(AuthenticationManager authenticationManager,
                           RepositoryPersonalized repositoryPersonalized,
                           RoleRepository roleRepository,
                           UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           TokenProvider jwtTokenUtil) {
        this.repositoryPersonalized = repositoryPersonalized;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Override
    public User saveUser(User user) throws DataAlreadyExistsException {
        //verificar que el username no este ocupado
        if (repositoryPersonalized.existByEmail(user.getEmail())){
            throw new DataAlreadyExistsException(HttpStatus.CONFLICT,"Email ocupada: "+user.getEmail());
        }
        //verificar que el documento no este ocupado
        if (repositoryPersonalized.existByDocument(user.getDocument())){
            throw new DataAlreadyExistsException(HttpStatus.CONFLICT,"Documento ocupada: "+user.getDocument());
        }
        //verificar que el email no este ocupado
        if (repositoryPersonalized.existByUsername(user.getUsername())){
            throw new DataAlreadyExistsException(HttpStatus.CONFLICT,"Username ocupada: "+user.getUsername());
        }
        //encriptar la contraseña
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        //guardar el usuario
        user.setId(repositoryPersonalized.UserID());
        User useraux = null;

        useraux = userRepository.save(user);



        return useraux;

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

    @Override
    public AuthToken authenticate(LoginUser loginUser) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return new AuthToken(token);
    }

    @Override
    public UserDto update(UserDto User) {
        return null;
    }

    @Override
    public String delete(Long id) {

        return "null";
    }
}
