package com.asps.clientes.api.controller;

import com.asps.clientes.domain.model.Cliente;
import com.asps.clientes.domain.service.CadastroClienteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    private final CadastroClienteService cadastroClienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente criar(@RequestBody @Valid Cliente cliente){
        return cadastroClienteService.salvar(cliente);
    }

    @PutMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente atualizar(@RequestBody Cliente cliente,@PathVariable String cpf){
        return cadastroClienteService.atualizar(cliente, cpf);
    }

    @PatchMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente atualizar(@RequestBody Map<String, Object> mapCliente, @PathVariable String cpf, HttpServletRequest request){

        final var cliente = atualizarCampos(mapCliente, cpf, request);

        return cadastroClienteService.atualizar(cliente, cpf);
    }
    
    @GetMapping
    public List<Cliente> consultarTodos(){
        return cadastroClienteService.consultarTodos();
    }

    @GetMapping("/filtrar")
    public Cliente filtrar(@RequestParam(required = false, name = "cpf") String cpf, @RequestParam(required = false, name = "id") Long id){
        if(id != null){
            return cadastroClienteService.consultar(id);
        }

        return cadastroClienteService.consultar(cpf);
    }

    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String cpf){
        cadastroClienteService.deletar(cpf);
    }

    private Cliente atualizarCampos(Map<String, Object> mapCliente, String cpf, HttpServletRequest request) {

        final var cliente = cadastroClienteService.consultar(cpf);

        return cadastroClienteService.atualizar(merge(mapCliente, cliente, request));
    }

    private Cliente clienteFromMap(Map<String, Object> mapCliente, HttpServletRequest request) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            return objectMapper.convertValue(mapCliente, Cliente.class);

        }catch (IllegalArgumentException e){
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    private Cliente merge(Map<String, Object> mapCliente, Cliente clienteAtualizado, HttpServletRequest request) {

        final Cliente clienteOrigem = clienteFromMap(mapCliente, request);

        mapCliente.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Cliente.class, key);
            field.setAccessible(true);

            Object valorAtualizado = ReflectionUtils.getField(field, clienteOrigem);

            ReflectionUtils.setField(field, clienteAtualizado, valorAtualizado);
        });

        return clienteAtualizado;
    }
}