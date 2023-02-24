package com.example.HibernatePostgreSecurityJWT.service.impl;

import com.example.HibernatePostgreSecurityJWT.dto.AuthToken;
import com.example.HibernatePostgreSecurityJWT.dto.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.entities.UserRole;
import com.example.HibernatePostgreSecurityJWT.exception.customizations.custom.DataAlreadyExistsException;
import com.example.HibernatePostgreSecurityJWT.exception.customizations.custom.UserModificationException;
import com.example.HibernatePostgreSecurityJWT.exception.customizations.custom.UserToDeleteNotFound;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.RoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.JPA.UserRoleRepository;
import com.example.HibernatePostgreSecurityJWT.repsitory.dao.RepositoryPersonalized;
import com.example.HibernatePostgreSecurityJWT.security.jwt.JwtAuthenticationFilter;
import com.example.HibernatePostgreSecurityJWT.security.jwt.TokenProvider;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    private final TokenProvider jwtTokenUtil;
    RepositoryPersonalized repositoryPersonalized;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    public UserServiceImpl(AuthenticationManager authenticationManager,
                           RepositoryPersonalized repositoryPersonalized,
                           RoleRepository roleRepository,
                           UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           TokenProvider jwtTokenUtil,
                           BCryptPasswordEncoder bcryptEncoder,
    JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.repositoryPersonalized = repositoryPersonalized;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.bcryptEncoder = bcryptEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Override
    public User saveUser(User user) throws DataAlreadyExistsException {
        verificactionUsuario(user);
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
        return repositoryPersonalized.findRoleByNameRol(role);
    }

    @Override
    public List<UserDto> findAllUser() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = repositoryPersonalized.findAllUser();
        users.forEach(x -> {
                    userDtos.add(new UserDto(x,repositoryPersonalized.findRolesByUsername(x.getUsername())));
                }
                );
        return userDtos;
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
    public User update(User userNew) {
        System.out.println("update");
        AtomicReference<Boolean> aprobado = new AtomicReference<>(false);
        //se obtiene el username del que esta haciendo la peticion
        List<String> roles = repositoryPersonalized.findRolesByUsername(jwtAuthenticationFilter.getUsername());
        //se obtiene el usuario para verificar que exista y generar un reporte
        User userOld = repositoryPersonalized.findUserById(userNew.getId());
        //se verifica que exista, si no existe se envia una exepcion
        if(userOld ==null){
            throw new UserToDeleteNotFound(HttpStatus.EXPECTATION_FAILED,"administrador "+jwtAuthenticationFilter.getUsername()+" intento eliminar un usuario invalido");
        }
        //se recorre los roles asignados para verificar que este permitido
        roles.forEach(rolecabecera -> {
            //si es empleado se verifica que la modificacion se este haciendo sobre el mismo
            if(rolecabecera.equalsIgnoreCase("EMPLOYEE")){
                User useraux = repositoryPersonalized.findUserByUsername(
                        jwtAuthenticationFilter.getUsername());
                if(useraux.getId()==userOld.getId()){
                    aprobado.set(true);
                    System.err.println("aprobado por ser empleado");
                }
            }
            //si es admin puede hacer sobre otros suarios
            if (rolecabecera.equalsIgnoreCase("ADMIN")){
                aprobado.set(true);
                System.err.println("aprobado por ser admin");
            }
        });
        if(aprobado.get()){
            verificactionUsuario(userNew);
            //genera un reporte indicando quien realizo la accion y a quien se borro
            userNew.setPassword(bcryptEncoder.encode(userNew.getPassword()));
            userRepository.save(userNew);
            userNew.setPassword(" ");
            userOld.setPassword(" ");
            logger.info(aprobado +" "
                    +jwtAuthenticationFilter.getUsername()+
                    " modifico "
                    +userOld+ " y quedo "+ userNew);
            return userNew;
        }
        throw new UserModificationException(HttpStatus.EXPECTATION_FAILED,
                " no fue aprobada la modificacion de "+jwtAuthenticationFilter.getUsername());

    }



    @Override
    public String delete(Long id) {
        //se obtiene el username del que esta haciendo la peticion
        List<String> roles = repositoryPersonalized.findRolesByUsername(jwtAuthenticationFilter.getUsername());
        //se obtiene el usuario para verificar que exista y generar un reporte
        User user = repositoryPersonalized.findUserById(id);
        //se verifica que exista, si no existe se envia una exepcion
        if(user ==null){
            throw new UserToDeleteNotFound(HttpStatus.EXPECTATION_FAILED,"administrador "+jwtAuthenticationFilter.getUsername()+" intento eliminar un usuario invalido");
        }
        //se quita la password para el reporte
        user.setPassword(" ");
        //se recorre los roles asignados para verificar que este permitido
        roles.forEach(x -> {
            if (x.equalsIgnoreCase("ADMIN")){
                //genera un reporte indicando quien realizo la accion y a quien se borro
                logger.info("administrador "
                        +jwtAuthenticationFilter.getUsername()+
                        " elimino a "
                        +user);

                userRepository.deleteById(id);
            }
        });
        //se retorna el nombre del usuario eliminado
        return "usuario "+user.getUsername()+" eliminado con exito";
    }
    //elimina los role asignados
    private void deleteUserRole(Long id){
        List<Long> ids = repositoryPersonalized.findIdUserRoleByUserId(id);
        ids.forEach(x->{
            userRoleRepository.deleteById(x);
        });
    }

    private void verificactionUsuario(User user){
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
    }
}
