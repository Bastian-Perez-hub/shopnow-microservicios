package com.example.moduloPedidos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/shopnow/pedido")
@Tag(name = "Comunicación entre Microservicios", description = "Endpoints que consultan información del módulo de Clientes")
public class ClientePedidoController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/clientes/{id}")
    @Operation(summary = "Consultar cliente desde módulo Pedidos", description = "Consulta la información de un cliente específico comunicándose internamente con el módulo de Clientes (puerto 8081)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado y retornado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al comunicarse con el módulo de Clientes")
    })
    public ResponseEntity<EntityModel<String>> checkCliente(
            @Parameter(description = "ID del cliente a consultar", required = true) @PathVariable Long id,
            @Parameter(description = "Token JWT de autorización (formato: Bearer {token})", required = true) @RequestHeader("Authorization") String token) {

        String url = "http://localhost:8081/api/v1/shopnow/cliente/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String body = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

        EntityModel<String> resource = EntityModel.of(body,
                linkTo(methodOn(ClientePedidoController.class).checkCliente(id, token)).withSelfRel(),
                linkTo(methodOn(ClientePedidoController.class).checkCliente(id, token)).withRel("cliente"));

        return ResponseEntity.ok(resource);
    }
}