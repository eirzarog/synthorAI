package dev.eirzarog.synthor.api.utils.validators.annotations;

import dev.eirzarog.synthor.api.utils.validators.EmailPatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailPatternValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailPattern {
    String message() default "Please provide a valid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
