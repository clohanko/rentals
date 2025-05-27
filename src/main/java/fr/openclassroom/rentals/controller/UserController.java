package fr.openclassroom.rentals.controller;

import fr.openclassroom.rentals.dto.CreateUserDto;
import fr.openclassroom.rentals.dto.UpdateUserDto;
import fr.openclassroom.rentals.dto.UserDetailDto;
import fr.openclassroom.rentals.dto.UserListDto;
import fr.openclassroom.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Lister tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs")
    @GetMapping
    public List<UserListDto> getAll() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Récupérer un utilisateur par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getById(
            @Parameter(description = "ID de l'utilisateur à récupérer") @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Créer un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping
    public ResponseEntity<UserDetailDto> create(
            @Parameter(description = "Données du nouvel utilisateur") @Valid @RequestBody CreateUserDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(dto));
    }

    @Operation(summary = "Mettre à jour un utilisateur existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailDto> update(
            @Parameter(description = "ID de l'utilisateur à mettre à jour") @PathVariable Long id,
            @Parameter(description = "Données de mise à jour de l'utilisateur") @Valid @RequestBody UpdateUserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @Operation(summary = "Supprimer un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de l'utilisateur à supprimer") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
