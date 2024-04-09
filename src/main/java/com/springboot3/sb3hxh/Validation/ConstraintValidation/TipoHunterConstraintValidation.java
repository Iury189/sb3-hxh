package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.*;
import com.springboot3.sb3hxh.Service.*;
import com.springboot3.sb3hxh.Validation.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;

public class TipoHunterConstraintValidation implements ConstraintValidator<TipoHunterValidation, TipoHunterEntity> {

    @Autowired
    private TipoHunterService tipoHunterService;

    @Override
    public void initialize(TipoHunterValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(TipoHunterEntity tipoHunterEntity, ConstraintValidatorContext context) {
        if (tipoHunterEntity == null) {
            return false;
        }
        String tipoHunterIdAsString = String.valueOf(tipoHunterEntity.getId());
        return tipoHunterService.existsId(tipoHunterIdAsString);
    }

}