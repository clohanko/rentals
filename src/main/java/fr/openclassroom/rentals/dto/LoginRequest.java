package fr.openclassroom.rentals.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class LoginRequest {

    @JsonProperty("email")
    @Schema(example = "test@gmail.com", description = "Adresse email utilisée pour se connecter")
    private String email;

    @JsonProperty("password")
    @Schema(example = "123soleil", description = "Mot de passe de l'utilisateur")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{email='" + email + "', password='" + password + "'}";
    }
}
