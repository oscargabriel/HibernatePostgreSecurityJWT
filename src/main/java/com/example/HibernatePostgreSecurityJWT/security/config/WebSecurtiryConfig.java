package com.example.HibernatePostgreSecurityJWT.security.config;

import com.example.HibernatePostgreSecurityJWT.exception.ExceptionRed.CustomAccessDeniedHandler;
import com.example.HibernatePostgreSecurityJWT.exception.ExceptionRed.UnauthorizedEntryPoint;
import com.example.HibernatePostgreSecurityJWT.security.jwt.JwtAuthenticationFilter;
import com.example.HibernatePostgreSecurityJWT.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * establece la configuracion de seguridad
 */
@Configuration
@EnableWebSecurity
public class WebSecurtiryConfig {

    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserServiceImpl userService;


    public WebSecurtiryConfig(UnauthorizedEntryPoint unauthorizedEntryPoint,
                              CustomAccessDeniedHandler accessDeniedHandler,
                              UserServiceImpl userService) {
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.userService = userService;
    }

    /**
     * filtra los mensajes, los que tengan acceso general y los que tengan roles
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                //
                .csrf().disable().
                //
                cors().disable()
                //establece los permisos de la direcciones
                .authorizeHttpRequests()
                //paginas que pueden ser accedidas sin estar autenticado
                .requestMatchers("/hola").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/findAllUser").permitAll()
                .requestMatchers("/authenticate").permitAll()

                //pagias restringidas para los roles en este caso, EMPLOYEE, USER y ADMIN
                .requestMatchers("/hello_employee").hasRole("EMPLOYEE")
                .requestMatchers("/hello_user").hasRole("USER")
                .requestMatchers("/hello_admin").hasRole("ADMIN")
                .requestMatchers("/hello_user_employee").hasAnyRole("USER","EMPLOYEE")

                //indica que hay que estar authenticado para cualquier otra pagina que no sea mencioanda
                //solo que no importara que rol tenga el usuario que este haciendo los llamados
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                //indica el mensaje creado en component cuando no esta authorizado
                .authenticationEntryPoint(unauthorizedEntryPoint)
                //indica el mensaje cuando el acceso es denegado
                .accessDeniedHandler(accessDeniedHandler)

                .and()
                //
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //indica para generar el token tiene que estar autenticado
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * funcion para la autenticacion
     * @param http
     * @param bCryptPasswordEncoder
     * @param userDetailsService
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailsService userDetailsService
    ) throws Exception{

        return http
                //indica la autenticacion
                .getSharedObject(AuthenticationManagerBuilder.class)
                //detalles del usuario
                .userDetailsService(userService)
                //el tipo de encriptacion para la verificacion
                .passwordEncoder(encoder())
                .and().build();
    }

    /**
     * indica donde estan las condiciones para la validacion de los token
     * @return
     * @throws Exception
     */
    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception{
        return new JwtAuthenticationFilter();
    }

    /**
     * indicar el metodo de encriptacion para la contraseña
     * @return
     */
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
