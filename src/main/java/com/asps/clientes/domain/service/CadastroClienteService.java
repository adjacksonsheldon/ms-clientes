package com.asps.clientes.domain.service;

import com.asps.clientes.core.security.UsuarioInfoHelper;
import com.asps.clientes.domain.exception.RegistroNaoEncontradoException;
import com.asps.clientes.domain.model.Cliente;
import com.asps.clientes.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CadastroClienteService {

    private final ClienteRepository clienteRepository;
    private final CadastroEstadoService cadastroEstadoService;

    private final UsuarioInfoHelper usuarioInfo;

    public Cliente salvar(Cliente cliente){

        final var estado = cadastroEstadoService.consultar(cliente.getEndereco().getEstado().getId());

        cliente.setUsuarioCadastroId(usuarioInfo.getUsuarioId());
        cliente.getEndereco().setEstado(estado);

        return clienteRepository.save(cliente);
    }

    public List<Cliente> consultarTodos(){
        return clienteRepository.findAll();
    }

    public Cliente consultar(String cpf){
        return clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));
    }

    public Cliente consultar(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));
    }

    public Cliente atualizar(Cliente cliente, String cpf){

        final var clienteConsultado = this.consultar(cpf);

        BeanUtils.copyProperties(cliente, clienteConsultado, "id");

        return this.atualizar(clienteConsultado);
    }

    public Cliente atualizar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void deletar(String cpf){
        final var cliente = this.consultar(cpf);

        clienteRepository.delete(cliente);
    }
}