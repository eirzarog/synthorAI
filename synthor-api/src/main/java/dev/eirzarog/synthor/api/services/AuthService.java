package dev.eirzarog.synthor.api.services;

import dev.eirzarog.synthor.api.entities.dtos.requests.auth.LoginRequestDTO;
import dev.eirzarog.synthor.api.entities.dtos.requests.auth.RegisterRequestDTO;
import dev.eirzarog.synthor.api.entities.dtos.responses.auth.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByUsername(username); // returns user
    }

    public LoginResponse authenticate(LoginRequestDTO request) {
        // Validate credentials, generate JWT
        String token = generateToken(request.getUsername());
        return new LoginResponse(token);
    }

    public void register(RegisterRequestDTO request) {
        // Save user to DB, encode password
    }

    private String generateToken(String username) {
        // JWT generation logic
        return "mock-jwt-token";
    }
}
