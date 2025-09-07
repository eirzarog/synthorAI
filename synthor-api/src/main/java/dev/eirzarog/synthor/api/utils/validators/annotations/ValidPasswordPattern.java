package dev.eirzarog.synthor.api.utils.validators.annotations;

import dev.eirzarog.synthor.api.utils.validators.PasswordPatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordPatternValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPasswordPattern {
    String message() default "Password must be 8–16 characters long, include uppercase, lowercase, digit, and special character";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}