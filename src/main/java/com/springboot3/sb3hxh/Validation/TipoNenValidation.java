package com.springboot3.sb3hxh.Validation;

import com.springboot3.sb3hxh.Validation.ConstraintValidation.*;
import jakarta.validation.*;
import java.lang.annotation.*;

@Constraint(validatedBy = TipoNenConstraintValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoNenValidation {

    String message() default "Tipo de Nen inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
