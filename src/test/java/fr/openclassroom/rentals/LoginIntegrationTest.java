package fr.openclassroom.rentals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenValidUser_thenLoginSucceeds() throws Exception {
        mockMvc.perform(formLogin()
                        .user("springuser")
                        .password("spring123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void whenInvalidUser_thenLoginFails() throws Exception {
        mockMvc.perform(formLogin()
                        .user("baduser")
                        .password("badpass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login?error"));
    }
}
