package fr.openclassroom.rentals.dto;

import fr.openclassroom.rentals.model.User;

public record UserListDto(Long id, String username, String email) {
    public static UserListDto from(User user) {
        return new UserListDto(user.getId(), user.getUsername(), user.getEmail());
    }
}

