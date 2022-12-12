package com.asps.clientes.api.controller;

import com.asps.clientes.domain.model.Cliente;
import com.asps.clientes.domain.model.Estado;
import com.asps.clientes.domain.repository.ClienteRepository;
import com.asps.clientes.domain.repository.EstadoRepository;
import com.asps.clientes.domain.util.DatabaseCleaner;
import com.asps.clientes.domain.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class ClienteControllerTestIt {

    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/clientes";

        databaseCleaner.clearTables();

        estadoRepository.save(Estado.builder().nome("Pernambuco").build());
        clienteRepository.save(gerarCliente1());
    }

    @Test
    public void deveConter0Clientes_QuandoConsultarClientes() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("", hasSize(1));
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarClientes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarClienteInexistente() {
        given()
                .pathParams("cpf", "54863738056")
                .accept(ContentType.JSON)
        .when()
                .get("/filtrar/{cpf}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCliente() {
        given()
                .body(getJsonNovoCliente())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarStatus200_QuandoAtualizarApenasNomeCliente() {
        given()
                .pathParams("cpf", "54863738056")
                .body(getJsonClienteApenasCpf())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .patch("{cpf}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("nome", equalTo("Jose Alberto"));
    }

    private Cliente gerarCliente1(){
        final var endereco = Cliente.Endereco.builder()
                .cep("123456")
                .bairro("xyz")
                .numero("123")
                .logradouro("a")
                .estado(Estado.builder().id(1L).build())
                .build();

        return Cliente.builder()
                .cpf("54863738056")
                .dataNascimento(LocalDate.now().plusYears(-20))
                .nome("João")
                .endereco(endereco)
                .build();
    }

    private String getJsonNovoCliente(){
        return ResourceUtils.getContentFromResource(
                "/json/cliente/cliente.json");
    }
    private String getJsonClienteApenasCpf(){
        return ResourceUtils.getContentFromResource(
                "/json/cliente/cliente.json");
    }
}