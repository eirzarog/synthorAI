package dev.eirzarog.synthor.api.entities.dtos.responses.user;

import dev.eirzarog.synthor.api.enumerators.UserRole;
import lombok.*;

// RESPONSE DTO (Output)
// Used in @GetMapping endpoints

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    // private String role; // role is a class
    // private List<String> roles; // e.g., ["ADMIN", "USER"]
    private UserRole role;

}

