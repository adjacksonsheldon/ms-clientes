package com.asps.clientes.api.controller;

import com.asps.clientes.domain.group.Groups;
import com.asps.clientes.domain.model.Empresa;
import com.asps.clientes.domain.service.CadastroEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/empresas")
public class EmpresaController {
    private final CadastroEmpresaService cadastroEmpresaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empresa criar(@RequestBody @Validated(Groups.EmpresaCadastro.class) Empresa empresa){
        return cadastroEmpresaService.salvar(empresa);
    }

    @PutMapping("/{cnpj}")
    @ResponseStatus(HttpStatus.OK)
    public Empresa atualizar(@RequestBody @Valid Empresa cliente, @PathVariable String cnpj){
        return cadastroEmpresaService.atualizar(cliente, cnpj);
    }

    @DeleteMapping("/{cnpj}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String cnpj){
        cadastroEmpresaService.deletar(cnpj);
    }

    @GetMapping
    public List<Empresa> consultarTodos(){
        return cadastroEmpresaService.consultarTodos();
    }

    @GetMapping("/filtrar")
    public Empresa consultarPorCnpj(@RequestParam(required = false, name = "cnpj") String cnpj, @RequestParam(required = false, name = "id") Long id){
        if(id != null){
            return cadastroEmpresaService.consultar(id);
        }

        return cadastroEmpresaService.consultar(cnpj);
    }
}