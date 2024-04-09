package com.springboot3.sb3hxh.Validation;

import com.springboot3.sb3hxh.Validation.ConstraintValidation.*;
import jakarta.validation.*;
import java.lang.annotation.*;

@Constraint(validatedBy = HunterConstraintValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HunterValidation {

    String message() default "Hunter inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
