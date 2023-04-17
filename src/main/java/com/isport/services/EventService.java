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

    public List<Event> getAllEvents() {
        return eventRepo.findAllOrderByEventDateTimeAsc();
    }

    public Event findEvent(Long id) {
        Optional<Event> optionalEvent = eventRepo.findById(id);
        return optionalEvent.orElse(null);
    }

    public void saveEvent(Event event) {
        eventRepo.save(event);
    }

    public void deleteEvent(Event event) {
        eventRepo.delete(event);
    }

    public List<Event> getUserEvents(User user) {
        return eventRepo.findAllByCreator(user);
    }

    public List<Event> getNonUserEvents(User user, Date todayDate) {
        return eventRepo.findByUsersNotContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(user, todayDate);
    }

    public List<Event> getTodayEvents(Date date) {
        List<Event> todayEvents = new ArrayList<>();
        List<Event> events = getAllEvents();

        for (Event e : events) {
            if (e.getEventDateTime().getDate() == date.getDate() && e.getEventDateTime().getMonth() == date.getMonth()
                    && e.getEventDateTime().getYear() == date.getYear()) {
                todayEvents.add(e);
            }
        }
        return todayEvents;
    }

    public List<Event> getEventsAfterTodayDate(Date todayDate) {
        return eventRepo.findByEventDateTimeIsAfterOrderByEventDateTimeAsc(todayDate);
    }

    public List<Event> findByEventName(String eventName, Date todayDate) {
        return eventRepo.findByEventNameContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(eventName, todayDate);
    }

    public List<Event> findByLocation(String location, Date todayDate) {
        return eventRepo.findByLocationContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(location, todayDate);
    }

    public List<Event> findByCreatorName(String creatorName, Date todayDate) {
        List<Event> creatorEvents = new ArrayList<>();
        List<Event> events = getAllEvents();
        for (Event e : events) {
            if (e.getCreator().getFullName().contains(creatorName) && e.getEventDateTime().after(todayDate))
                creatorEvents.add(e);
        }
        return creatorEvents;
    }
}
