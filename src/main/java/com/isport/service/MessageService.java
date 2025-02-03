package com.isport.service;

import com.isport.model.Message;
import com.isport.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> eventMessages(Long eventId) {
        return messageRepository.findByEventId(eventId);
    }

    public void addMessage(Message message) {
         messageRepository.save(message);
    }

    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    public List<Message> getUserEventMessages(Long userId, Long eventId) {
        return getAllMessages().stream()
                .filter(message -> message.getUser().getId().equals(userId) && message.getEvent().getId().equals(eventId))
                .collect(Collectors.toList());
    }
}
