package com.example.HibernatePostgreSecurityJWT.security.jwt;

import com.example.HibernatePostgreSecurityJWT.exception.customizations.BadCredentialsLoginFailed;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * verifiaca que el string personalizado para el token sea correcto
     */
    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    /**
     * prefijo para la cabecera
     */
    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    /**
     * llama al metodo para verificar la authenticacion
     */
    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    /**
     * llama a la calse que genero el token
     */
    @Autowired
    private TokenProvider jwtTokenUtil;


    /**
     * filtra el token y devuelve expeciones segun sea el caso
     * @param req
     * @param res
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String header = req.getHeader(HEADER_STRING);
        String username = null;
        String password = null;
        String authToken = null;
        //verifica que la cabecerao este vacial
        if (header != null && header.startsWith(TOKEN_PREFIX)) {

            authToken = header.replace(TOKEN_PREFIX,"");
            //obtiene el username y devuelve diferentes expecciones dado el caso
            try {
                username = jwtTokenUtil.getUserNameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("Error: El obtener el nombre de usuario del token", e);
                } catch (ExpiredJwtException e) {
                logger.warn("Error: El token a expirado", e);
            } catch(SignatureException e){
                logger.error("Error: El usuario o la contraseña no son validos",e);
            } catch (MalformedJwtException e){
                logger.error("cadena mal formada",e);
            }

        } else {
            logger.warn("No se pudo encontrar la cadena portadora, se ignorará el encabezado");
        }
        //si el usuario no es nulo y no hay contexto de authenticacion
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //establece el contexto de seguridad
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {

                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthenticationToken(
                        authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                logger.info("Usuario autenticado " + username + ", establecer el contexto de seguridad");

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        try {
            chain.doFilter(req, res);
        }catch (BadCredentialsLoginFailed e){

        }
    }
}
