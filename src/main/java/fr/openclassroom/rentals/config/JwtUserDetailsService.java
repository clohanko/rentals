package fr.openclassroom.rentals.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.openclassroom.rentals.model.User;
import fr.openclassroom.rentals.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        // 1️⃣  username → 2️⃣  email
        User u = userRepository.findByUsername(login)
                .orElseGet(() -> userRepository.findByEmail(login)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "Utilisateur introuvable : " + login)));

        // Construction du UserDetails Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())   // le “principal”
                .password(u.getPassword())       // hash BCrypt stocké
                .authorities("ROLE_USER")        // à adapter si rôles
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
