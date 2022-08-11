package com.isport.repositories;

import com.isport.models.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findAll();
    List<Message> findByEventIdIs(Long id);
}