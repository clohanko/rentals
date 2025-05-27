package fr.openclassroom.rentals.service;

import fr.openclassroom.rentals.model.Message;
import java.util.List;
import fr.openclassroom.rentals.controller.dto.MessageResponse;
import fr.openclassroom.rentals.controller.dto.MessageRequest;

public interface MessageService {
    List<Message> findAll();
    List<MessageResponse> getAllMessages();

    Message findById(Long id);
    Message create(Message message);
    Message update(Long id, Message message);
    void deleteMessage(Long id, String username);
    MessageResponse getMessageById(Long id);
    MessageResponse createMessage(MessageRequest req, String username);
    MessageResponse updateMessage(Long id, MessageRequest req, String username);



}