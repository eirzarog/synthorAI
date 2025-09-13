package dev.eirzarog.synthor.api.entities.dtos.requests.user;

import dev.eirzarog.synthor.api.enumerators.UserRole;
import dev.eirzarog.synthor.api.utils.validators.annotations.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDTO {

    @NotBlank(message = "Username is required")
    @ValidUsernameAvailability
    @ValidUsernamePattern
    private String username;

    @NotBlank(message = "Email is required")
    @ValidEmailAvailability
    @ValidEmailPattern
    private String email;

    @NotBlank(message = "Password is required")
    @ValidPasswordPattern
    private String password;

    private String firstName;
    private String lastName;

    @Builder.Default
    private UserRole role = UserRole.USER;
}

