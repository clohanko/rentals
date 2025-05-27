package fr.openclassroom.rentals.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank String username,
        @Email @NotBlank String email,
        @NotBlank String password
) {}
