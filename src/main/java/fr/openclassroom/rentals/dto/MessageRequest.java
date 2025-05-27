package fr.openclassroom.rentals.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MessageRequest(
        @NotNull Long rentalId,
        @NotNull Long userId,
        @NotBlank String message
) {}
