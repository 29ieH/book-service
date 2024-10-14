package _ieh.example.book_service.validater.Validator;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import _ieh.example.book_service.validater.Constraints.YearReleaseConstraint;

public class YearReleaseValidator implements ConstraintValidator<YearReleaseConstraint, Integer> {

    @Override
    public void initialize(YearReleaseConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer i, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate date = LocalDate.now();
        int yearNow = date.getYear();
        return i >= yearNow;
    }
}
