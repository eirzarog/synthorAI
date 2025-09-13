package dev.eirzarog.synthor.api.entities.dtos.responses.user;

import dev.eirzarog.synthor.api.enumerators.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

// Detailed profile (for /me endpoint)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profileImage;
    private String bio;
    private Instant dateOfBirth;
    private boolean emailVerified;
    private boolean active;
    private Instant createdAt;
    private UserRole role;

}
