package fr.openclassroom.rentals.service;

import fr.openclassroom.rentals.model.Message;
import fr.openclassroom.rentals.model.User;
import fr.openclassroom.rentals.model.Rental;
import fr.openclassroom.rentals.repository.UserRepository;
import fr.openclassroom.rentals.repository.RentalRepository;
import fr.openclassroom.rentals.repository.MessageRepository;
import fr.openclassroom.rentals.controller.dto.MessageRequest;
import fr.openclassroom.rentals.controller.dto.MessageResponse;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findById(Long id) {
        return messageRepository.findById(id).orElseThrow();
    }

    @Override
    public Message create(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message update(Long id, Message messageData) {
        Message message = findById(id);
        message.setContent(messageData.getContent());
        return messageRepository.save(message);
    }

    @Override
    public List<MessageResponse> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(MessageResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public MessageResponse getMessageById(Long id) {
        Message m = messageRepository.findById(id).orElseThrow();
        return MessageResponse.from(m);
    }

    @Override
    public MessageResponse createMessage(MessageRequest req, String username) {
        User sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED));

        if (!sender.getId().equals(req.userId())) {
            throw new ResponseStatusException(FORBIDDEN, "User mismatch");
        }

        Rental rental = rentalRepository.findById(req.rentalId()).orElseThrow();

        Message message = new Message();
        message.setContent(req.message());
        message.setSender(sender);
        message.setRental(rental);

        return MessageResponse.from(messageRepository.save(message));
    }

    @Override
    public MessageResponse updateMessage(Long id, MessageRequest req, String username) {
        Message existing = messageRepository.findById(id).orElseThrow();

        if (!existing.getSender().getUsername().equals(username)
                || !existing.getSender().getId().equals(req.userId())) {
            throw new ResponseStatusException(FORBIDDEN, "Not allowed");
        }

        existing.setContent(req.message());
        Message updated = messageRepository.save(existing);

        return MessageResponse.from(updated);
    }

    @Override
    public void deleteMessage(Long id, String username) {
        Message existing = messageRepository.findById(id).orElseThrow();

        if (!existing.getSender().getUsername().equals(username)) {
            throw new ResponseStatusException(FORBIDDEN, "Not allowed");
        }

        messageRepository.deleteById(id);
    }
}
