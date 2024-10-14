package _ieh.example.book_service.validater.Constraints;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import _ieh.example.book_service.validater.Validator.YearReleaseValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {YearReleaseValidator.class})
public @interface YearReleaseConstraint {
    String message() default "Year release require then by now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
