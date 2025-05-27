package fr.openclassroom.rentals.repository;

import fr.openclassroom.rentals.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}