package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.*;
import com.springboot3.sb3hxh.Service.*;
import com.springboot3.sb3hxh.Validation.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class TipoNenConstraintValidation implements ConstraintValidator<TipoNenValidation, TipoNenEntity> {

    @Autowired
    private TipoNenService tipoNenService;

    @Override
    public void initialize(TipoNenValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(TipoNenEntity tipoNenEntity, ConstraintValidatorContext context) {
        if (tipoNenEntity == null) {
            return false;
        }
        String tipoNenIdAsString = String.valueOf(tipoNenEntity.getId());
        return tipoNenService.existsId(tipoNenIdAsString);
    }

}