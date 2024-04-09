package com.springboot3.sb3hxh.Validation;

import com.springboot3.sb3hxh.Validation.ConstraintValidation.*;
import jakarta.validation.*;
import java.lang.annotation.*;

@Constraint(validatedBy = TipoHunterConstraintValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoHunterValidation {

    String message() default "Tipo de Hunter inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}