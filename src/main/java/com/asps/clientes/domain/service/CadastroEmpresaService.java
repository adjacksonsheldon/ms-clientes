package com.asps.clientes.domain.service;

import com.asps.clientes.domain.exception.RegistroDuplicadoException;
import com.asps.clientes.domain.exception.RegistroNaoEncontradoException;
import com.asps.clientes.domain.model.Empresa;
import com.asps.clientes.domain.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CadastroEmpresaService {

    private final EmpresaRepository empresaRepository;

    public Empresa salvar(Empresa empresa){
        try {
            return  empresaRepository.save(empresa);
        }catch (DataIntegrityViolationException e) {
            throw new RegistroDuplicadoException("Empresa cadastrado anteriormente.", e);
        }
    }

    public List<Empresa> consultarTodos(){
        return empresaRepository.findAll();
    }

    public Empresa consultar(String cnpj){
        return empresaRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Empresa não encontrado."));
    }

    public Empresa consultar(Long id){
        return empresaRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Empresa não encontrado."));
    }

    public Empresa atualizar(Empresa empresa, String cnpj){

        final var empresaConsultado = this.consultar(cnpj);

        BeanUtils.copyProperties(empresa, empresaConsultado, "id", "dataCriacao");

        return this.atualizar(empresaConsultado);
    }

    public Empresa atualizar(Empresa empresa){
        return empresaRepository.save(empresa);
    }

    public void deletar(String cnpj){
        final var empresa = this.consultar(cnpj);

        empresaRepository.delete(empresa);
    }
}