package fr.openclassroom.rentals.controller;

import fr.openclassroom.rentals.controller.dto.MessageRequest;
import fr.openclassroom.rentals.controller.dto.MessageResponse;
import fr.openclassroom.rentals.service.MessageService;
import fr.openclassroom.rentals.service.RentalService;
import fr.openclassroom.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final RentalService rentalService;

    public MessageController(
            MessageService messageService,
            UserService userService,
            RentalService rentalService
    ) {
        this.messageService = messageService;
        this.userService = userService;
        this.rentalService = rentalService;
    }

    @Operation(summary = "Récupérer tous les messages")
    @ApiResponse(responseCode = "200", description = "Liste des messages")
    @GetMapping
    public ResponseEntity<List<MessageResponse>> getAll() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @Operation(summary = "Récupérer un message par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message trouvé"),
            @ApiResponse(responseCode = "404", description = "Message non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> getById(
            @Parameter(description = "ID du message") @PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @Operation(summary = "Créer un nouveau message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message créé"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping
    public ResponseEntity<MessageResponse> create(
            @RequestBody(description = "Données du message", required = true,
                    content = @Content(schema = @Schema(implementation = MessageRequest.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody MessageRequest req,
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "username") String username
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(messageService.createMessage(req, username));
    }

    @Operation(summary = "Mettre à jour un message existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message mis à jour"),
            @ApiResponse(responseCode = "404", description = "Message non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> update(
            @Parameter(description = "ID du message à mettre à jour") @PathVariable Long id,
            @RequestBody(description = "Données mises à jour du message", required = true,
                    content = @Content(schema = @Schema(implementation = MessageRequest.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody MessageRequest req,
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "username") String username
    ) {
        return ResponseEntity.ok(messageService.updateMessage(id, req, username));
    }

    @Operation(summary = "Supprimer un message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Message supprimé"),
            @ApiResponse(responseCode = "404", description = "Message non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID du message à supprimer") @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal(expression = "username") String username
    ) {
        messageService.deleteMessage(id, username);
        return ResponseEntity.noContent().build();
    }
}
