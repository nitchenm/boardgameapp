package com.example.boardgameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boardgameapp.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long>{
    
}
