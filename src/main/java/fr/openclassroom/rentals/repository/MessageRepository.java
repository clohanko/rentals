package fr.openclassroom.rentals.repository;

import fr.openclassroom.rentals.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}