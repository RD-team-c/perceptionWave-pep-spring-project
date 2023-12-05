package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message findByMessageId(Integer id) {
        return messageRepository.findByMessageId(id);
    }

    public List<Message> findByPostedBy(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public int saveAndReturnRowsUpdated(Message message) {
        messageRepository.save(message);
        return 1;
    }

    public int delete(Message message) {
        messageRepository.delete(message);
        return 1;
    }

}
