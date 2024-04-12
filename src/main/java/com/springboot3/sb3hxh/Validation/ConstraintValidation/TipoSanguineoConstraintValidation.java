package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.*;
import com.springboot3.sb3hxh.Service.*;
import com.springboot3.sb3hxh.Validation.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class TipoSanguineoConstraintValidation implements ConstraintValidator<TipoSanguineoValidation, TipoSanguineoEntity>  {

    @Autowired
    private TipoSanguineoService tipoSanguineoService;

    @Override
    public void initialize(TipoSanguineoValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(TipoSanguineoEntity tipoSanguineoEntity, ConstraintValidatorContext context) {
        if (tipoSanguineoEntity == null) {
            return false;
        }
        String tipoSanguineoIdAsString = String.valueOf(tipoSanguineoEntity.getId());
        return tipoSanguineoService.existsId(tipoSanguineoIdAsString);
    }

}
