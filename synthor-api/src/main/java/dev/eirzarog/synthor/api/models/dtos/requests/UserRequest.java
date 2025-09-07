package dev.eirzarog.synthor.api.models.dtos.requests;

import dev.eirzarog.synthor.api.utils.validators.annotations.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class UserRequest {

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @ValidUsernamePattern
    @ValidUsernameAvailability
    private String username;

    @NotBlank(message = "Email is required")
    @ValidEmailPattern
    @ValidEmailAvailability
    private String email;

    @NotBlank(message = "Password is required")
    @ValidPasswordPattern
    private String password;

}
