package com.isport.repositories;

import com.isport.models.Event;
import com.isport.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByEmail(String email);
    User findByIdIs(Long id);
    List<User> findAllByEvents(Event event);
    List<User> findByEventsNotContains(Event event);
}