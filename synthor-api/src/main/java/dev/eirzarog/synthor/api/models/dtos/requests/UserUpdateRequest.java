package dev.eirzarog.synthor.api.models.dtos.requests;

import dev.eirzarog.synthor.api.utils.validators.annotations.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserUpdateRequest {



    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;


    @ValidUsernameAvailability
    @ValidUsernamePattern
    private String username;

    @ValidEmailAvailability
    @ValidEmailPattern
    private String email;


    @ValidPasswordPattern
    private String password;


}
