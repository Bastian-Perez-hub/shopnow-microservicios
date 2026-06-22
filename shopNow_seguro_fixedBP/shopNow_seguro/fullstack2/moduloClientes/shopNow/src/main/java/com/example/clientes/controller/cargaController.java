package com.example.clientes.controller;

import com.example.clientes.dto.clienteDTO;
import com.example.clientes.service.CargaMasivaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shopnow/cliente")
@Tag(name = "Carga Masiva", description = "Operaciones de carga masiva de clientes")
public class cargaController {

    @Autowired
    private CargaMasivaService service;

    @PostMapping("/masivo")
    @Operation(
        summary = "Carga masiva de clientes",
        description = "Recibe una lista de clientes en formato JSON y los registra todos de una vez en la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carga completada exitosamente"),
        @ApiResponse(responseCode = "400", description = "La lista está vacía o es inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno durante la carga")
    })
    public ResponseEntity<?> cargar(@RequestBody List<clienteDTO> nuevoCliente) {
        try {
            if (nuevoCliente == null || nuevoCliente.isEmpty()) {
                return ResponseEntity.badRequest().body("La lista está vacía");
            }

            long inicio = System.currentTimeMillis();
            service.procesarCarga(nuevoCliente);
            long fin = System.currentTimeMillis();

            return ResponseEntity.ok("Éxito: " + nuevoCliente.size() + " procesados en " + (fin - inicio) + "ms");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error en la carga: " + e.getMessage());
        }
    }
}
