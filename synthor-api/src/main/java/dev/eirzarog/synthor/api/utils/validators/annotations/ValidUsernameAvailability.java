package dev.eirzarog.synthor.api.utils.validators.annotations;

import dev.eirzarog.synthor.api.utils.validators.UsernameAvailabilityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameAvailabilityValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsernameAvailability {
    String message() default "Username is already taken.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
