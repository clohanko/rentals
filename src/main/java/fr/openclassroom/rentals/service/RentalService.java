package fr.openclassroom.rentals.service;

import fr.openclassroom.rentals.dto.CreateRentalDto;
import fr.openclassroom.rentals.dto.RentalDetailDto;
import fr.openclassroom.rentals.model.Rental;
import org.springframework.web.multipart.MultipartFile;
import fr.openclassroom.rentals.dto.RentalListDto;


import java.util.List;
import java.util.Optional;

public interface RentalService {
    List<Rental> findAll();
    List<RentalListDto> getAllRentals(); // garde juste la signature
    Rental findByIdOrThrow(Long id);
    Rental save(Rental rental);
    void delete(Long id);

    RentalDetailDto getRentalById(Long id);
    RentalDetailDto createRental(CreateRentalDto dto, MultipartFile picture, String username);
    RentalDetailDto updateRental(Long id, CreateRentalDto dto, MultipartFile picture, String username);



}
