package dev.eirzarog.synthor.api.utils.validators.annotations;

import dev.eirzarog.synthor.api.utils.validators.EmailAvailabilityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailAvailabilityValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailAvailability {
    String message() default "Email is already taken.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
