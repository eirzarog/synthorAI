package dev.eirzarog.synthor.api.utils.validators;

import dev.eirzarog.synthor.api.utils.validators.annotations.ValidPasswordPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordPatternValidator implements ConstraintValidator<ValidPasswordPattern, String> {

//    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
//    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n";

    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,16}$";

    private Pattern pattern;

    @Override
    public void initialize(ValidPasswordPattern constraintAnnotation) {
        pattern = Pattern.compile(PASSWORD_REGEX);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;
        return pattern.matcher(password).matches();


    }
}
