package com.isport.services;

import com.isport.models.Event;
import com.isport.models.User;
import com.isport.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepo;

    public EventService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<Event> allEvents(){
        return eventRepo.findAll();
    }

    public Event findById(Long id){
        Optional<Event> optionalEvent = eventRepo.findById(id);
        if(optionalEvent.isPresent()) return optionalEvent.get();
        else return null;
    }

    public Event addEvent(Event event){
        return eventRepo.save(event);
    }

    public Event updateEvent(Event event){
        return eventRepo.save(event);
    }

    public void deleteEvent(Event event){
        eventRepo.delete(event);
    }

    public List<Event> getUserEvents(User user){
        return eventRepo.findAllByCreator(user);
    }

    public List<Event> getNonUserEvents(User user){
        return eventRepo.findByUsersNotContains(user);
    }

    public List<Event> getTodaysEvents(Date date){
        List<Event> todaysEvents = new ArrayList<>();
        List<Event> events = allEvents();

        for(Event e : events){
            if(e.getDate().getDate() == date.getDate() && e.getDate().getMonth() == date.getMonth()
                && e.getDate().getYear() == date.getYear()) {
                todaysEvents.add(e);
            }
        }
        return todaysEvents;
    }

    public List<Event> getEventsByName(String eventName){
        return eventRepo.findByEventNameContains(eventName);
    }

    public List<Event> getEventsByLocation(String location){
        return eventRepo.findByLocationContains(location);
    }

    public List<Event> getEventsByCreatorName(String creatorName){
        List<Event> creatorEvents = new ArrayList<>();
        List<Event> events = allEvents();
        for(Event e : events){
            if(e.getCreator().fullName().contains(creatorName)) creatorEvents.add(e);
        }
        return creatorEvents;
    }

    public List<Event> getEventsAfterDate(Date date){
        return eventRepo.findAllByDateAfter(date);
    }
}