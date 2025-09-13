package dev.eirzarog.synthor.api.entities.dtos.requests.auth;

import dev.eirzarog.synthor.api.utils.validators.annotations.ValidEmailAvailability;
import dev.eirzarog.synthor.api.utils.validators.annotations.ValidEmailPattern;
import dev.eirzarog.synthor.api.utils.validators.annotations.ValidUsernameAvailability;
import dev.eirzarog.synthor.api.utils.validators.annotations.ValidUsernamePattern;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// there are no relationships and references to other objects, if we need the
// will dtos to refer to them preventing circular references
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Username is required")
    @ValidUsernameAvailability
    @ValidUsernamePattern
    private String username;

    @NotBlank(message = "Password is required")
    @ValidEmailAvailability
    @ValidEmailPattern
    private String password;

    @Builder.Default
    private Boolean rememberMe = false;
}
