package com.isport.service;

import com.isport.model.Event;
import com.isport.model.User;
import com.isport.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAllByOrderByEventDateTimeAsc();
    }

    public Event findEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        return optionalEvent.orElse(null);
    }

    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    public List<Event> getNonUserEvents(User user, Date todayDate) {
        return eventRepository.findByUsersNotContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(user, todayDate);
    }

    public List<Event> getTodayEvents(Date date) {
        return getAllEvents().stream()
                .filter(event -> event.getEventDateTime().getTime() == date.getTime())
                .collect(Collectors.toList());
    }

    public List<Event> getEventsAfterTodayDate(Date todayDate) {
        return eventRepository.findByEventDateTimeIsAfterOrderByEventDateTimeAsc(todayDate);
    }

    public List<Event> findByEventName(String eventName, Date todayDate) {
        return eventRepository.findByEventNameContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(eventName, todayDate);
    }

    public List<Event> findByLocation(String location, Date todayDate) {
        return eventRepository.findByLocationContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(location, todayDate);
    }

    public List<Event> findByCreatorName(String creatorName, Date todayDate) {
        return getAllEvents().stream()
                .filter(event -> event.getCreator().getFullName().contains(creatorName) && event.getEventDateTime().after(todayDate))
                .collect(Collectors.toList());
    }
}
