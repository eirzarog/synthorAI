package dev.eirzarog.synthor.api.utils.validators;

import dev.eirzarog.synthor.api.services.UserService;
import dev.eirzarog.synthor.api.utils.validators.annotations.ValidEmailAvailability;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailAvailabilityValidator implements ConstraintValidator<ValidEmailAvailability, String> {
    private final UserService userService;

    @Autowired
    public EmailAvailabilityValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(ValidEmailAvailability constraintAnnotation) {
        // Optional: initialization logic
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return userService.isEmailAvailable(email);
    }
}
