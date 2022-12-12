package com.asps.clientes.core.validator;

import com.asps.clientes.domain.model.Empresa;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

/**
 * Valida a obrigação do preenchimento de ao menos um
 */
public class NomeEmpresaValidator implements ConstraintValidator<ValidacaoNomeEmpresa, Empresa> {

    @Override
    public boolean isValid(Empresa empresa, ConstraintValidatorContext constraintValidatorContext) {

        if((isNull(empresa.getRazaoSocial())
                && isNull(empresa.getNomeFantasia()))){
            return false;
        }

        return true;
    }
}
