package com.example.boardgameapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="eventos")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String location; // Lugar fisico o virtual

    @Column(nullable=false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host; //Usuario que organiza el evento

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game; //Juego al que se jugara


}
