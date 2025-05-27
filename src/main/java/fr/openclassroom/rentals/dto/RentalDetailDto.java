package fr.openclassroom.rentals.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fr.openclassroom.rentals.model.Rental;
import java.math.BigDecimal;
import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RentalDetailDto(
        Long id,
        String name,
        Integer surface,
        BigDecimal price,
        String description,
        String picture,
        Long ownerId,
        Instant createdAt,
        Instant updatedAt
) {
    public static RentalDetailDto from(Rental r) {
        return new RentalDetailDto(
                r.getId(),
                r.getName(),
                r.getSurface(),
                r.getPrice(),
                r.getDescription(),
                r.getPicture(),
                r.getOwner().getId(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    }
}
