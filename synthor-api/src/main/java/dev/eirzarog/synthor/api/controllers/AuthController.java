package dev.eirzarog.synthor.api.controllers;

import dev.eirzarog.synthor.api.entities.dtos.requests.auth.LoginRequestDTO;
import dev.eirzarog.synthor.api.entities.dtos.requests.auth.RegisterRequestDTO;
import dev.eirzarog.synthor.api.entities.dtos.responses.auth.LoginResponse;
import dev.eirzarog.synthor.api.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    /**
     *
     *This setup assumes you're using Spring Security and a JWT-based authentication system.
     * If you're interested in integrating OAuth2, LDAP, or session-based login instead,
     * I can tailor the example for that too
     *
     */

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDTO request) {
        LoginResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }
}
