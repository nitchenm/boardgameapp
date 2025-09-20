package com.example.boardgameapp.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Este filtro personalizado intercepta toda peticion http
//Su principal tarea es extraer el JWT del header de autorizacion
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService){
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException{
        //Obtengo la autorizacion header del request
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        //Chequeo si el header existe y comienza con bearer
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            //Extraigo el token y remuevo el prefijo bearer
            jwt = authHeader.substring(7);
            //extraigo el usuario del token
            username = jwtUtils.getUsernameFromJwtToken(jwt);
        }
        //Si el username fue encontrado y el usuario no esta ya autenticado 
        if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null){
            //Cargo los detalles de usuario desde la bdd
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //Valido token
            if(jwtUtils.validateJwtToken(jwt)){
                //Creo un token de autenticacion y lo seteo al contexto de seguridad
                UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
