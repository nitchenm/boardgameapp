package com.example.boardgameapp.config;

import org.springframework.stereotype.Component;

import java.util.Date;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}
