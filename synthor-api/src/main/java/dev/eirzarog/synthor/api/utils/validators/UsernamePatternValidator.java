package dev.eirzarog.synthor.api.utils.validators;

import dev.eirzarog.synthor.api.utils.validators.annotations.ValidUsernamePattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UsernamePatternValidator implements ConstraintValidator<ValidUsernamePattern, String> {


    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$";

    private Pattern pattern;

    @Override
    public void initialize(ValidUsernamePattern constraintAnnotation) {
        pattern = Pattern.compile(USERNAME_REGEX);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return pattern.matcher(username).matches();

    }

}
