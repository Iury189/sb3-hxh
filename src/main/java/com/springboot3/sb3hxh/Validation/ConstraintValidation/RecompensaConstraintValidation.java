package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.*;
import com.springboot3.sb3hxh.Service.*;
import com.springboot3.sb3hxh.Validation.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;

public class RecompensaConstraintValidation implements ConstraintValidator<RecompensaValidation, RecompensaEntity> {

    @Autowired
    private RecompensaService recompensaService;

    @Override
    public void initialize(RecompensaValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(RecompensaEntity recompensaEntity, ConstraintValidatorContext context) {
        if (recompensaEntity == null) {
            return false;
        }
        String recompensaIdAsString = String.valueOf(recompensaEntity.getId());
        return recompensaService.existsId(recompensaIdAsString);
    }

}