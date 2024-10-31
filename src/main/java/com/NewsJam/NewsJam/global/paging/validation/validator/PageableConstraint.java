package com.NewsJam.NewsJam.global.paging.validation.validator;

import com.NewsJam.NewsJam.global.enums.statuscode.ErrorStatus;
import com.NewsJam.NewsJam.global.paging.validation.annotation.Pageable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PageableConstraint implements ConstraintValidator<Pageable, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean isValid = value > 0;
        if (!isValid) {
            log.error("validation 에러 발생");
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus._PAGE_VARIABLE_INVALID.getMessage())
                    .addConstraintViolation();
        }
        return isValid;
    }

    @Override
    public void initialize(Pageable constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}


