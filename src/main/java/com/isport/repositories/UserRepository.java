package com.isport.repositories;

import com.isport.models.Event;
import com.isport.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    User findByIdIs(Long id);

    User findByEmail(String email);

    List<User> findAllByEvents(Event event);

    List<User> findByEventsNotContains(Event event);

    @Query(value = "SELECT * FROM `your-schema`.`users` WHERE id != 1;", nativeQuery = true)
    List<User> getNonAdminUsers();
}
