package com.asps.clientes.api.controller;

import com.asps.clientes.api.helper.MapToObjectHelper;
import com.asps.clientes.core.security.CheckSecurity;
import com.asps.clientes.domain.model.Cliente;
import com.asps.clientes.domain.service.CadastroClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    private final CadastroClienteService cadastroClienteService;
    private final MapToObjectHelper<Cliente> mapToObjectHelper;

    @CheckSecurity.PodeAlterar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criar(@RequestBody @Valid Cliente cliente){
        return cadastroClienteService.salvar(cliente);
    }

    @CheckSecurity.PodeAlterar
    @PutMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente atualizar(@RequestBody Cliente cliente,@PathVariable String cpf){
        return cadastroClienteService.atualizar(cliente, cpf);
    }

    @CheckSecurity.PodeAlterar
    @PatchMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente atualizar(@RequestBody Map<String, Object> mapCliente, @PathVariable String cpf, HttpServletRequest request) throws MethodArgumentNotValidException {

        final var cliente = cadastroClienteService.consultar(cpf);

        final var clienteAtualizado = mapToObjectHelper.merge(mapCliente, cliente, request, Cliente.class);

        return cadastroClienteService.atualizar(clienteAtualizado);
    }
    
    @GetMapping
    @CheckSecurity.IsAuthenticated
    public List<Cliente> consultarTodos(){
        return cadastroClienteService.consultarTodos();
    }

    @GetMapping("/filtrar")
    @CheckSecurity.IsAuthenticated
    public Cliente filtrar(@RequestParam(required = false, name = "cpf") String cpf, @RequestParam(required = false, name = "id") Long id){
        if(id != null){
            return cadastroClienteService.consultar(id);
        }

        return cadastroClienteService.consultar(cpf);
    }

    @CheckSecurity.PodeAlterar
    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String cpf){
        cadastroClienteService.deletar(cpf);
    }
}