package fr.openclassroom.rentals.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(
        @NotBlank String username,
        @Email @NotBlank String email
) {}
