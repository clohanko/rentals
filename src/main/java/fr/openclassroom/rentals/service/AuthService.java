package fr.openclassroom.rentals.service;

import fr.openclassroom.rentals.dto.RegisterRequest;
import fr.openclassroom.rentals.dto.UserMeDto;
import fr.openclassroom.rentals.dto.JwtResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void register(RegisterRequest request);
    UserMeDto getCurrentUser(Authentication authentication);
    JwtResponse registerAndAuthenticate(RegisterRequest request);
}
