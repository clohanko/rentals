package fr.openclassroom.rentals.controller;

import fr.openclassroom.rentals.dto.CreateRentalDto;
import fr.openclassroom.rentals.dto.RentalListDto;
import fr.openclassroom.rentals.dto.RentalDetailDto;
import fr.openclassroom.rentals.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@Validated
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(summary = "Lister toutes les locations disponibles")
    @ApiResponse(responseCode = "200", description = "Liste des locations")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<RentalListDto>> getAll() {
        return Map.of("rentals", rentalService.getAllRentals());
    }

    @Operation(summary = "Obtenir les détails d’une location par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location trouvée"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalDetailDto> getById(
            @Parameter(description = "ID de la location") @PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @Operation(summary = "Créer une nouvelle location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location créée"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalDetailDto> create(
            @Valid @ModelAttribute CreateRentalDto dto,
            @Parameter(description = "Photo de la location") @RequestPart(required = false) MultipartFile picture,
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "username") String username
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rentalService.createRental(dto, picture, username));
    }

    @Operation(summary = "Mettre à jour une location existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location mise à jour"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RentalDetailDto> update(
            @Parameter(description = "ID de la location à mettre à jour") @PathVariable Long id,
            @Valid @ModelAttribute CreateRentalDto dto,
            @Parameter(description = "Nouvelle image de la location") @RequestPart(required = false) MultipartFile picture,
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "username") String username
    ) {
        return ResponseEntity.ok(rentalService.updateRental(id, dto, picture, username));
    }

    @Operation(summary = "Supprimer une location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location supprimée"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la location à supprimer") @PathVariable Long id) {
        rentalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
