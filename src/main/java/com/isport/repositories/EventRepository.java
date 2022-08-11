package com.isport.repositories;

import com.isport.models.Event;
import com.isport.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findAll();
    Event findByIdIs(Long id);
    List<Event> findAllByCreator(User user);
    List<Event> findByUsersNotContains(User user);
    List<Event> findByEventNameContains(String eventName);
    List<Event> findByLocationContains(String location);
    List<Event> findAllByDateAfter(Date date);
}