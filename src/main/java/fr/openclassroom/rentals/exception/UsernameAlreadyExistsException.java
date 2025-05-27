package fr.openclassroom.rentals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyExistsException extends ResponseStatusException {
    public UsernameAlreadyExistsException(String username) {
        super(HttpStatus.BAD_REQUEST, "Ce nom d’utilisateur est déjà utilisé : " + username);
    }
}
