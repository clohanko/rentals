// src/main/java/fr/openclassroom/rentals/controller/dto/MessageResponse.java
package fr.openclassroom.rentals.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MessageResponse(
        Long    id,
        Long    rentalId,
        Long    senderId,
        String  message,
        Instant createdAt
) {
    public static MessageResponse from(fr.openclassroom.rentals.model.Message m) {
        return new MessageResponse(
                m.getId(),
                m.getRental().getId(),
                m.getSender().getId(),
                m.getContent(),       // champ JPA content mappé sur column "message"
                m.getCreatedAt()
        );
    }
}
