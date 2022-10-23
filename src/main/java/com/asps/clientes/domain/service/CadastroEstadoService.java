package com.asps.clientes.domain.service;

import com.asps.clientes.domain.exception.RegistroDuplicadoException;
import com.asps.clientes.domain.exception.RegistroNaoEncontradoException;
import com.asps.clientes.domain.model.Estado;
import com.asps.clientes.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CadastroEstadoService {

    private final EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){
        try {
            return  estadoRepository.save(estado);
        }catch (DataIntegrityViolationException e) {
            throw new RegistroDuplicadoException("Estado cadastrado anteriormente.", e);
        }
    }

    public List<Estado> consultarTodos(){
        return estadoRepository.findAll();
    }


    public Estado consultar(Long id){
        return estadoRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Estado não encontrado."));
    }

    public Estado atualizar(Estado estado, Long id){

        final var estadoConsultado = this.consultar(id);

        BeanUtils.copyProperties(estado, estadoConsultado, "id", "dataCriacao");

        return this.atualizar(estadoConsultado);
    }

    public Estado atualizar(Estado estado){
        return estadoRepository.save(estado);
    }

    public void deletar(Long id){
        final var estado = this.consultar(id);

        estadoRepository.delete(estado);
    }
}