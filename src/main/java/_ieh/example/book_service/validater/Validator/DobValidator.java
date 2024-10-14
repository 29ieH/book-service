package _ieh.example.book_service.validater.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import _ieh.example.book_service.validater.Constraints.DobConstraint;

public class DobValidator implements ConstraintValidator<DobConstraint, Date> {
    int min;

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate value = date.toLocalDate();
        long year = ChronoUnit.YEARS.between(value, LocalDate.now());
        return year >= min;
    }
}
