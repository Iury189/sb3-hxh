package com.springboot3.sb3hxh.Validation.ConstraintValidation;

import com.springboot3.sb3hxh.Entity.*;
import com.springboot3.sb3hxh.Service.*;
import com.springboot3.sb3hxh.Validation.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class HunterConstraintValidation implements ConstraintValidator<HunterValidation, HunterEntity> {

    @Autowired
    private HunterService hunterService;

    @Override
    public void initialize(HunterValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(HunterEntity hunterEntity, ConstraintValidatorContext context) {
        if (hunterEntity == null) {
            return false;
        }
        String hunterIdAsString = String.valueOf(hunterEntity.getId());
        return hunterService.existsId(hunterIdAsString);
    }

}
