package com.example.boardgameapp.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


//Manejo metodos para validar, generar y extraer informacion desde el JWT
@Component
public class JwtUtils {
    //Esta llave secreta la llamo desde properties (debo generar una string aleatoria)
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    //Aqui genero el objeto de secretKey desde la string secreta
    //Esta llave la uso para iniciar y verificar los JWT
    private SecretKey getSigningKey(){
        //Genero una llave utilizable para el algoritmo HMAC-SHA 
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //Aqui genero el JWT para el usuario autenticado 
    public String generateJwtToken(Authentication authentication){
        //extraigo los detalles de usuario del objeto autenticado
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        //Aqui construyo el JWT con el sujeto(username) la fecha que se creo, expiracion y firma
        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + expiration))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    //Obtengo el username del JWT
    public String getUsernameFromJwtToken(String token){
        //parseo el token para verificar su firma y extraer el claims (payload)
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    //Valido el JWT 
    public boolean validateJwtToken(String authToken){
        try {
            //Intento parsear y verificar el token si falla salta exception 
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
