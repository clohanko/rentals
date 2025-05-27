package fr.openclassroom.rentals.service;

import fr.openclassroom.rentals.dto.RegisterRequest;
import fr.openclassroom.rentals.dto.JwtResponse;
import fr.openclassroom.rentals.dto.UserMeDto;
import fr.openclassroom.rentals.exception.UserNotFoundException;
import fr.openclassroom.rentals.exception.UsernameAlreadyExistsException;
import fr.openclassroom.rentals.model.User;
import fr.openclassroom.rentals.repository.UserRepository;
import fr.openclassroom.rentals.config.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public JwtResponse registerAndAuthenticate(RegisterRequest request) {
        register(request);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String jwt = jwtUtil.generateToken(authentication.getName());
        return new JwtResponse(jwt);
    }

    @Override
    public UserMeDto getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            throw new UserNotFoundException("Utilisateur non connecté");
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return new UserMeDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
