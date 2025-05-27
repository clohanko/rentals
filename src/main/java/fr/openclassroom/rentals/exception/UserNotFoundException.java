package fr.openclassroom.rentals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {


    public UserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "Utilisateur introuvable avec le nom : " + username);
    }
}
