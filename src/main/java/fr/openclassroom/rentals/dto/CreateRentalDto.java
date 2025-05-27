package fr.openclassroom.rentals.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "Données nécessaires pour créer une location")
public record CreateRentalDto(

        @NotBlank
        @Schema(description = "Nom du bien immobilier", example = "Appartement T2 lumineux à Paris")
        String name,

        @Positive
        @Schema(description = "Surface en mètres carrés", example = "45")
        Integer surface,

        @Positive
        @Schema(description = "Prix mensuel de la location", example = "1200.50")
        BigDecimal price,

        @Size(max = 2000)
        @Schema(description = "Description détaillée du bien", example = "Situé au cœur de Montmartre, proche métro et commerces.")
        String description

) {}
