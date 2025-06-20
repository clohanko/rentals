package fr.openclassroom.rentals.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank @Size(min = 3, max = 50)
    @JsonAlias("name")
    private String username;

    @Email @NotBlank
    private String email;

    @NotBlank @Size(min = 6, max = 100)
    private String password;

    // 🔑 getters & setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
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
}
