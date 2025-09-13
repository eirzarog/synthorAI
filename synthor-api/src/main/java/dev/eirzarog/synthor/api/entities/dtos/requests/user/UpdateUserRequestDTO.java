package dev.eirzarog.synthor.api.entities.dtos.requests.user;

import dev.eirzarog.synthor.api.utils.validators.annotations.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UpdateUserRequestDTO {



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
