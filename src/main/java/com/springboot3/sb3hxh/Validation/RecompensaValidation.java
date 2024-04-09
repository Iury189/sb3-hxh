package com.springboot3.sb3hxh.Validation;

import com.springboot3.sb3hxh.Validation.ConstraintValidation.*;
import jakarta.validation.*;
import java.lang.annotation.*;

@Constraint(validatedBy = RecompensaConstraintValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RecompensaValidation {

    String message() default "Recompensa inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}