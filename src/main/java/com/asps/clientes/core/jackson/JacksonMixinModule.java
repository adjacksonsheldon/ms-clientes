package com.asps.clientes.core.jackson;

import com.asps.clientes.api.model.ClienteMixin;
import com.asps.clientes.api.model.EmpresaMixin;
import com.asps.clientes.api.model.EstadoMixin;
import com.asps.clientes.domain.model.Cliente;
import com.asps.clientes.domain.model.Empresa;
import com.asps.clientes.domain.model.Estado;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cliente.class, ClienteMixin.class);
        setMixInAnnotation(Estado.class, EstadoMixin.class);
        setMixInAnnotation(Empresa.class, EmpresaMixin.class);
    }
}
