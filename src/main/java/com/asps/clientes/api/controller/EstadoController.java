package com.asps.clientes.api.controller;

import com.asps.clientes.domain.model.Estado;
import com.asps.clientes.domain.service.CadastroEstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/estados")
public class EstadoController {
    private final CadastroEstadoService cadastroEstadoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado criar(@RequestBody Estado estado){
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Estado atualizar(@RequestBody @Valid Estado estado, @PathVariable Long id){
        return cadastroEstadoService.atualizar(estado, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        cadastroEstadoService.deletar(id);
    }

    @GetMapping
    public List<Estado> consultarTodos(){
        return cadastroEstadoService.consultarTodos();
    }

    @GetMapping("/{id}")
    public Estado consultar(@PathVariable Long id){
        if(id != null){
            return cadastroEstadoService.consultar(id);
        }

        return cadastroEstadoService.consultar(id);
    }
}