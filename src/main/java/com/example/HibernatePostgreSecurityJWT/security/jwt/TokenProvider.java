package com.example.HibernatePostgreSecurityJWT.security.jwt;

import io.jsonwebtoken.lang.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.datatype.DatatypeConstants;
import java.io.Serializable;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * genera el token
 */
@Component
public class TokenProvider implements Serializable {

    /**
     * tiempo de vida del token
     */
    @Value("${jwt.token.validity}")
    public long TOKEN_VALIDITY;

    /**
     * llave para la generacion del token string>256b
     */
    @Value("${jwt.signing.key}")
    public String SIGNING_KEY;

    /**
     * verifica las autorizaciones que llevara el token
     */
    @Value("${jwt.authorities.key}")
    public String AUTHORITIES_KEY;

    /**
     * indica el username que tiene el token
     */

    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * indica la fecha de expliracion del token
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * verifica si el string personalizado para el token es valido
     * @param token
     * @param claimsResolver
     * @return
     * @param <T>
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * un nivel mas profundo para la verifiacion del token
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * indica si el token a expirado
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * generar el token
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication){
        //indica las authorizaciones del usuario(admin,user...)
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        //indica el algoritmo asignado para la llave (256 bits)
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //crea los bist con el la llave propia
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(SIGNING_KEY);
        //codifica la llave
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //genera el token con toda la informacion que va llevar
        return Jwts.builder()
                //indica el usuario al que corresponde el token
                .setSubject(authentication.getName())
                //indica las authorizaciones con una clave propia
                .claim(AUTHORITIES_KEY, authorities)
                //indica el date inicial del token (se reinicia el contador cada vez que el usuario hace llamdas)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //indica el tiempo de expiracion sumandole el tiempo que se establece
                .setExpiration( new Date(System.currentTimeMillis() + TOKEN_VALIDITY) )
                //se añade la clave del token
                .signWith(signingKey,signatureAlgorithm)

                .compact();
    }

    /**
     * validacion del token
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUserNameFromToken(token);
        //verifica que el username sea correcto y que no este expirado
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * autentica el token
     * @param token
     * @param existingAuth
     * @param userDetails
     * @return
     */
    UsernamePasswordAuthenticationToken getAuthenticationToken(final String token,
                                                               final Authentication existingAuth,
                                                               final UserDetails userDetails) {
        //separa los componentes del token
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        //verifica y devuelve
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
