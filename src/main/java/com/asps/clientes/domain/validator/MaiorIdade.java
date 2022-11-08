package com.asps.clientes.domain.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { MaiorIdadeValidator.class })
public @interface MaiorIdade {
    String message() default "{MaiorIdade}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
