package com.example.boardgameapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.boardgameapp.entity.User;
import com.example.boardgameapp.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //Busco al usuario en la bdd usando el repo
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        //Construyo y devuelvo un objeto Spring Security UserDetails
        //Contiene el usuario, contraseña hasheada y roles
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER") //rol asignado para cada usuario autenticado
                .build();
    }
}
