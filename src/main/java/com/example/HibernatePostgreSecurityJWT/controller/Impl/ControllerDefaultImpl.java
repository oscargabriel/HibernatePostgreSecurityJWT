package com.example.HibernatePostgreSecurityJWT.controller.Impl;

import com.example.HibernatePostgreSecurityJWT.controller.ControllerDefault;
import com.example.HibernatePostgreSecurityJWT.dto.LoginUser;
import com.example.HibernatePostgreSecurityJWT.dto.UserDto;
import com.example.HibernatePostgreSecurityJWT.dto.AuthToken;
import com.example.HibernatePostgreSecurityJWT.entities.Role;
import com.example.HibernatePostgreSecurityJWT.entities.User;
import com.example.HibernatePostgreSecurityJWT.exception.customizations.custom.InvalidExpressionException;
import com.example.HibernatePostgreSecurityJWT.security.jwt.TokenProvider;
import com.example.HibernatePostgreSecurityJWT.service.UserService;
import com.example.HibernatePostgreSecurityJWT.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * recibe mensajes desde http GET, POST, PUT y DELETE
 * usa los roles para discriminar los accesos
 *
 * tiene una copia de userService para para que se encargeue de procesar el contenido de los mensajes
 * Authentication manager y tokenproviden para la autenticacion y generacion del token (si se ejecuta en service se genera un conflicto de llamda)
 *
 * los response de la clase gestionan respuestas acertivas, para respuestas negativas hay throw que gestioan el caso
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ControllerDefaultImpl implements ControllerDefault {
    Logger logger = LoggerFactory.getLogger(ControllerDefaultImpl.class);

    private String regex = null;
    private Pattern pat = null;
    private Matcher math = null;

    @Autowired
    private final UserService userService;




    public ControllerDefaultImpl(UserServiceImpl userService) {
        this.userService = userService;

    }

    @Override
    @GetMapping("/hola")
    public ResponseEntity<String> hola(){
        System.out.println("saludo");
        return ResponseEntity.ok("hola");
    }

    @Override
    @GetMapping("/hola_authenticate")
    public ResponseEntity<String> holaauthenticate() {
        System.out.println("authenticate");
        return ResponseEntity.ok("hola authenticate");
    }
    @Override
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello_user")
    public ResponseEntity<String> userPing() {
        return ResponseEntity.ok("hola USER");
    }
    @Override
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/hello_employee")
    public ResponseEntity<String> employeePing() {
        return ResponseEntity.ok("hola EMPLOYEE");
    }
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello_admin")
    public ResponseEntity<String> adminPing(){
        return ResponseEntity.ok("hola ADMIN");
    }

    @Override
    @PreAuthorize("hasAnyRole('USER','EMPLOYEE')")
    @GetMapping("/hello_user_employee")
    public ResponseEntity<String> useremployeeePing() {
        return ResponseEntity.ok("hola USER_EMPLOYEE");
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody User user){
        logger.info("registrando usuario "+user.getUsername());
        //validaciones que las expreciones regulares sean correctas
        validarExprecionRegular(user.getEmail(), "email");
        validarExprecionRegular(user.getUsername(), "username");
        validarExprecionRegular(user.getPassword(), "password");
        validarExprecionRegular(user.getPhone(), "phone");
        validarExprecionRegular(user.getName(), "name");
        validarExprecionRegular(user.getLastName(), "lastname");
        //guarda el usuario
            User userAux = userService.saveUser(user);
        //busca el rol por defecto a asignar
        Role role = userService.findRoleByrol("USER");
        //asigna el rol y return
        return ResponseEntity.ok().body(userService.saveRoleByUser(userAux,role));

    }

    @Override
    @GetMapping("/findAllUser")
    public ResponseEntity<List<UserDto>> findAllUser() {
        logger.info("mostrando todos los usuarios");
        List<UserDto> users = userService.findAllUser();
        return ResponseEntity.ok().body(users);
    }

    @Override
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginUser loginUser){
        logger.info("autenticando a l usuario "+loginUser.getUsername());
        validarExprecionRegular(loginUser.getPassword(), "password");
        validarExprecionRegular(loginUser.getUsername(), "username");
        return ResponseEntity.ok(userService.authenticate(loginUser));
    }



    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        logger.info("actualizando al usuario: "+user.getUsername());
        validarExprecionRegular(user.getEmail(), "email");
        validarExprecionRegular(user.getUsername(), "username");
        validarExprecionRegular(user.getPassword(), "password");
        validarExprecionRegular(user.getPhone(), "phone");
        validarExprecionRegular(user.getName(), "name");
        validarExprecionRegular(user.getLastName(), "lastname");
        return ResponseEntity.ok(userService.update(user));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logger.info("eliminando al usuario con id: "+id);
        return ResponseEntity.ok(userService.delete(id));
    }

    /**
     * funcion para validar las expreciones regulares que se reciven desde autenticacion o registro
     * si la cadena es aceptada continuara, de ser negativa se crea una expepcion InvalidExpressionExcepction
     * y se envia al usuario el tipo que fallo
     * @param cadena la cadena que va verificar (name, phone, email, password, username...)
     * @param tipo el tipo de cadena que corresponde
     */
    protected void validarExprecionRegular(String cadena, String tipo){
        switch (tipo) {
            case "email":
                regex = "^([A-Za-z0-9]{1})([-_\\.A-Za-z0-9]+)([A-Za-z0-9]{1})*@+([A-Za-z0-9\\.]+)*([A-Za-z]{2,})$";
                break;
            case "username":
                regex = "^([A-Za-z0-9]{1})([A-Za-z0-9-_\\.]{2,20})([A-Za-z0-9]{1})$";
                break;
            case "password":
                regex = "^([A-Za-z0-9-_@#%\\.\\+\\$\\*]{4,30})$";
                break;
            case "phone":
                regex = "^([\\+]{0,1})([0-9]{3,})$";
                break;
            case "name":
            case "lastname":
                regex = "^([A-Za-z]{4,30})$";
                break;
            case "fecha":
                regex = "^([0-9]{1,2})/([0-9]{1,2})/([0-9]{2,4})$";
                break;

            default:
                regex=null;
        }
        if(regex!=null) {
            Pattern pat = Pattern.compile(regex);
            math = pat.matcher(cadena);
            if(math.find()){
                throw new InvalidExpressionException(HttpStatus.PRECONDITION_FAILED,"el campo de" +tipo+"no es valido");
            }
        }

    }



}
