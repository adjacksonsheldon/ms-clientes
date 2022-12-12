package com.asps.clientes.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { NomeEmpresaValidator.class })
public @interface ValidacaoNomeEmpresa {
    String message() default "O nome fantasia ou razão social deve ser preenchido.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
