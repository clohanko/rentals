package fr.openclassroom.rentals.service;

import fr.openclassroom.rentals.dto.RentalDetailDto;
import fr.openclassroom.rentals.dto.RentalListDto;
import fr.openclassroom.rentals.dto.CreateRentalDto;
import fr.openclassroom.rentals.model.Rental;
import fr.openclassroom.rentals.model.User;
import fr.openclassroom.rentals.repository.RentalRepository;
import fr.openclassroom.rentals.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final Path uploadDir;

    public RentalServiceImpl(RentalRepository rentalRepository, UserRepository userRepository,
                             @Value("${rentals.upload-dir:uploads}") Path uploadDir) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.uploadDir = uploadDir;
    }

    @Override
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Override
    public Rental findByIdOrThrow(Long id) {
        return rentalRepository.findById(id).orElseThrow();
    }

    @Override
    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public void delete(Long id) {
        rentalRepository.deleteById(id);
    }

    @Override
    public RentalDetailDto createRental(CreateRentalDto dto, MultipartFile picture, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED));

        Rental rental = buildRental(dto, owner, picture);
        Rental saved = rentalRepository.save(rental);
        return RentalDetailDto.from(saved);
    }

    @Override
    public List<RentalListDto> getAllRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(RentalListDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public RentalDetailDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow();
        return RentalDetailDto.from(rental);
    }

    @Override
    public RentalDetailDto updateRental(Long id, CreateRentalDto dto, MultipartFile picture, String username) {
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED));

        Rental existing = rentalRepository.findById(id).orElseThrow();

        if (!existing.getOwner().getUsername().equals(username)) {
            throw new ResponseStatusException(FORBIDDEN, "Vous n'êtes pas autorisé à modifier cette location");
        }

        existing.setName(dto.name());
        existing.setSurface(dto.surface());
        existing.setPrice(dto.price());
        existing.setDescription(dto.description());

        if (picture != null && !picture.isEmpty()) {
            try {
                Files.createDirectories(uploadDir);
                String filename = System.currentTimeMillis() + "_" + picture.getOriginalFilename();
                Files.copy(picture.getInputStream(), uploadDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploads/")
                        .path(filename)
                        .toUriString();
                existing.setPicture(url);
            } catch (IOException e) {
                throw new RuntimeException("Échec de l’enregistrement de l’image", e);
            }
        }

        return RentalDetailDto.from(rentalRepository.save(existing));
    }

    private Rental buildRental(CreateRentalDto dto, User owner, MultipartFile picture) {
        Rental rental = new Rental();
        rental.setName(dto.name());
        rental.setSurface(dto.surface());
        rental.setPrice(dto.price());
        rental.setDescription(dto.description());
        rental.setOwner(owner);

        if (picture != null && !picture.isEmpty()) {
            try {
                Files.createDirectories(uploadDir);
                String filename = System.currentTimeMillis() + "_" + picture.getOriginalFilename();
                Files.copy(picture.getInputStream(), uploadDir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploads/")
                        .path(filename)
                        .toUriString();
                rental.setPicture(url);
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }

        return rental;
    }
}
