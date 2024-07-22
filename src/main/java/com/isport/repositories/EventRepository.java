package com.isport.repositories;

import com.isport.models.Event;
import com.isport.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAll();

    List<Event> findAllByCreator(User user);

    List<Event> findByEventDateTimeIsAfterOrderByEventDateTimeAsc(Date date);

    List<Event> findByUsersNotContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(User user, Date date);

    List<Event> findByEventNameContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(String eventName, Date date);

    List<Event> findByLocationContainsAndEventDateTimeIsAfterOrderByEventDateTimeAsc(String location, Date date);

    @Query(value = "SELECT * FROM `your-schema`.`events` ORDER BY event_date_time ASC;", nativeQuery = true)
    List<Event> findAllOrderByEventDateTimeAsc();
}
