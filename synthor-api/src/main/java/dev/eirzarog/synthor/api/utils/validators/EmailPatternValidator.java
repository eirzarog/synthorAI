package dev.eirzarog.synthor.api.utils.validators;

import dev.eirzarog.synthor.api.utils.validators.annotations.ValidEmailPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


@Component
public class EmailPatternValidator implements ConstraintValidator<ValidEmailPattern, String> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private Pattern pattern;

    @Override
    public void initialize(ValidEmailPattern constraintAnnotation) {
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

}