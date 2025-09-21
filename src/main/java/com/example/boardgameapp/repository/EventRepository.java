package com.example.boardgameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.boardgameapp.entity.Event;


public interface EventRepository extends JpaRepository<Event, Long> {

}
