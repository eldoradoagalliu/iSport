package com.isport.repository;

import com.isport.model.Event;
import com.isport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByOrderByEventDateTimeAsc();

    List<Event> findByEventDateTimeIsAfterOrderByEventDateTimeAsc(Date date);

    List<Event> findByUsersNotContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(User user, Date date);

    List<Event> findByEventNameContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(String eventName, Date date);

    List<Event> findByLocationContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(String location, Date date);
}
