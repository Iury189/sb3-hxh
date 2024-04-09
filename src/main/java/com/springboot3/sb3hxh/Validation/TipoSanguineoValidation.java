package com.springboot3.sb3hxh.Validation;

import com.springboot3.sb3hxh.Validation.ConstraintValidation.*;
import jakarta.validation.*;
import java.lang.annotation.*;

@Constraint(validatedBy = TipoSanguineoConstraintValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoSanguineoValidation {

    String message() default "Tipo sanguíneo inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
