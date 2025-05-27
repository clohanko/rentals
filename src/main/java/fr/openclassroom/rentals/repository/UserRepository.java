// File: src/main/java/fr/openclassroom/rentals/repository/UserRepository.java
package fr.openclassroom.rentals.repository;

import fr.openclassroom.rentals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);       // ← ajouté
    boolean existsByUsername(String username);       // ← ajouté
    // (optionnel) boolean existsByEmail(String email);
}
