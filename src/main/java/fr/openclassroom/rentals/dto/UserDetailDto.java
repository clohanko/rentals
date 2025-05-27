package fr.openclassroom.rentals.dto;

import fr.openclassroom.rentals.model.User;

import java.time.Instant;

public record UserDetailDto(Long id, String username, String email, boolean enabled, Instant createdAt) {
    public static UserDetailDto from(User user) {
        return new UserDetailDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled(),
                user.getCreatedAt()
        );
    }
}
