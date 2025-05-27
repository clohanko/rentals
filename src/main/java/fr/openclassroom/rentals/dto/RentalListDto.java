package fr.openclassroom.rentals.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fr.openclassroom.rentals.model.Rental;
import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RentalListDto(
        Long id,
        String name,
        Integer surface,
        BigDecimal price,
        String picture,
        String description,
        Long ownerId
) {
    public static RentalListDto from(Rental r) {
        return new RentalListDto(
                r.getId(),
                r.getName(),
                r.getSurface(),
                r.getPrice(),
                r.getPicture(),
                r.getDescription(),
                r.getOwner().getId()
        );
    }
}
