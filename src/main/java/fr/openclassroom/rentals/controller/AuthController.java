package fr.openclassroom.rentals.controller;

import fr.openclassroom.rentals.dto.LoginRequest;
import fr.openclassroom.rentals.dto.RegisterRequest;
import fr.openclassroom.rentals.dto.UserMeDto;
import fr.openclassroom.rentals.dto.JwtResponse;
import fr.openclassroom.rentals.service.AuthService;
import fr.openclassroom.rentals.config.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Enregistrer un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur enregistré"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données d'enregistrement",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class)))
            @Valid @RequestBody RegisterRequest req) {
        try {
            JwtResponse jwtResponse = authService.registerAndAuthenticate(req);
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Erreur d'authentification après inscription"));
        }
    }


    @Operation(summary = "Connexion utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie, JWT retourné"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "401", description = "Identifiants incorrects")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("📩 Login = " + request.getEmail() + ", password = " + request.getPassword());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String jwt = jwtUtil.generateToken(authentication.getName());
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Identifiants invalides"));
        }
    }

    @Operation(
            summary = "Obtenir les informations de l'utilisateur connecté",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Données de l'utilisateur connecté"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @GetMapping("/me")
    public UserMeDto me(@Parameter(hidden = true) Authentication authentication) {
        return authService.getCurrentUser(authentication);
    }

}
