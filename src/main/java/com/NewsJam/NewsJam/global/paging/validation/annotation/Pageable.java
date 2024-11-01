package com.NewsJam.NewsJam.global.paging.validation.annotation;

import com.NewsJam.NewsJam.global.paging.validation.validator.PageableConstraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PageableConstraint.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pageable {
    String message() default ("페이지 관련 값은 0보다 커야합니다.");

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
