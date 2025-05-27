package fr.openclassroom.rentals.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public record UserMeDto(
        Long id,
        @JsonProperty("name") String username,
        String email,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) {}
