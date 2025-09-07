package dev.eirzarog.synthor.api.models.dtos.responses;

import dev.eirzarog.synthor.api.enums.UserRole;
import dev.eirzarog.synthor.api.enums.UserStatus;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String username;
    private String email;
}
