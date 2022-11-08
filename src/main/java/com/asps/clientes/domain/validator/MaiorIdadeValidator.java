package com.asps.clientes.domain.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MaiorIdadeValidator implements ConstraintValidator<MaiorIdade, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        if((LocalDate.now()).plusYears(-18).compareTo(value) <0 ){
            return false;
        }

        return true;
    }
}
