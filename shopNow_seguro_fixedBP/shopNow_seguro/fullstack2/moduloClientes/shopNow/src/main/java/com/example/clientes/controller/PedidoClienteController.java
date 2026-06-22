package com.example.clientes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/shopnow/cliente")
@Tag(name = "Comunicación entre Microservicios", description = "Endpoints que consultan información del módulo de Pedidos")
public class PedidoClienteController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/pedidos/{id}")
    @Operation(
        summary = "Consultar pedido desde módulo Clientes",
        description = "Consulta la información de un pedido específico comunicándose internamente con el módulo de Pedidos (puerto 8082)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado y retornado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error al comunicarse con el módulo de Pedidos")
    })
    public String checkPedido(
        @Parameter(description = "ID del pedido a consultar", required = true)
        @PathVariable Long id,
        @Parameter(description = "Token JWT de autorización (formato: Bearer {token})", required = true)
        @RequestHeader("Authorization") String token) {

        String url = "http://localhost:8082/api/v1/shopnow/pedido/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }
}