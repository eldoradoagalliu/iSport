package com.isport.services;

import com.isport.models.Message;
import com.isport.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    public List<Message> allMessages(){
        return messageRepo.findAll();
    }

    public List<Message> eventMessages(Long eventId){
        return messageRepo.findByEventIdIs(eventId);
    }

    public Message addMessage(Message message){
        return messageRepo.save(message);
    }

    public void deleteMessage(Message message){
        messageRepo.delete(message);
    }
}