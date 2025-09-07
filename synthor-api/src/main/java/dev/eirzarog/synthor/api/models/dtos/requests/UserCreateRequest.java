package dev.eirzarog.synthor.api.models.dtos.requests;

import dev.eirzarog.synthor.api.utils.validators.annotations.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // ? for create user request
public class UserCreateRequest {

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
}

