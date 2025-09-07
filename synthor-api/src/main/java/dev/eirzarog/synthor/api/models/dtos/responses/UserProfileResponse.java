package dev.eirzarog.synthor.api.models.dtos.responses;

import dev.eirzarog.synthor.api.enums.UserRole;
import dev.eirzarog.synthor.api.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
