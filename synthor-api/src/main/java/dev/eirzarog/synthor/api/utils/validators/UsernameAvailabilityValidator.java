package dev.eirzarog.synthor.api.utils.validators;

import dev.eirzarog.synthor.api.services.UserService;
import dev.eirzarog.synthor.api.utils.validators.annotations.ValidUsernameAvailability;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UsernameAvailabilityValidator implements ConstraintValidator<ValidUsernameAvailability, String> {

    private final UserService userService;

    @Autowired
    public UsernameAvailabilityValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(ValidUsernameAvailability constraintAnnotation) {
        // Optional: initialization logic
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return userService.isUsernameAvailable(username);
    }
}
