package com.asps.clientes.domain.mapper;

import com.asps.clientes.domain.model.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmpresaMapper {

    EmpresaMapper INSTANCE = Mappers.getMapper(EmpresaMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    void updateEmpresa(Empresa empresa, @MappingTarget Empresa empresaTarget);
}
