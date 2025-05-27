package fr.openclassroom.rentals.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil   = jwtUtil;
        // pas de setFilterProcessesUrl ici : SecurityConfig le fait déjà
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            Map<String,String> creds =
                    new ObjectMapper().readValue(req.getInputStream(), Map.class);

            String id       = creds.getOrDefault("username", creds.get("email")); // 👈
            String password = creds.get("password");

            var token = new UsernamePasswordAuthenticationToken(id, password);
            return authManager.authenticate(token);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Lecture des identifiants impossible", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth)
            throws IOException, ServletException {

        String token = jwtUtil.generateToken(auth.getName());

        res.setHeader("Authorization", "Bearer " + token);
        res.setContentType("application/json");
        res.getWriter().write("{\"token\":\"" + token + "\"}");
    }
}
