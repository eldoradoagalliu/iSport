package com.isport.services;

import com.isport.models.Message;
import com.isport.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepo;

    public MessageService(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public List<Message> eventMessages(Long eventId) {
        return messageRepo.findByEventIdIs(eventId);
    }

    public void addMessage(Message message) {
         messageRepo.save(message);
    }

    public void deleteMessage(Message message) {
        messageRepo.delete(message);
    }

    public List<Message> getUserEventMessages(Long userId, Long eventId) {
        List<Message> userEventMessages = new ArrayList<>();
        for (Message message : getAllMessages()) {
            if (message.getUser().getId().equals(userId) && message.getEvent().getId().equals(eventId)) {
                userEventMessages.add(message);
            }
        }
        return userEventMessages;
    }
}
