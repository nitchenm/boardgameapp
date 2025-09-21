package com.example.boardgameapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boardgameapp.entity.Event;
import com.example.boardgameapp.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event){
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }

    public Event getEventById(Long id){
        return eventRepository.findById(id).orElse(null);
    }

    public void deleteEvent(Long id){
        eventRepository.deleteById(id);
    }
}
