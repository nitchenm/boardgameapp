package com.example.boardgameapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boardgameapp.entity.Game;
import com.example.boardgameapp.repository.GameRepository;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game createGame(Game game){
        return gameRepository.save(game);
    }

    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }
}
