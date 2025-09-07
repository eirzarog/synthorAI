package dev.eirzarog.synthor.api.utils.validators.annotations;

import dev.eirzarog.synthor.api.utils.validators.UsernamePatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernamePatternValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsernamePattern {
    String message() default "Username must be 3-20 characters long and contain only letters, numbers, and underscores.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
