package _ieh.example.book_service.validater.Constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import _ieh.example.book_service.validater.Validator.DobValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "You must be than {min} year";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
