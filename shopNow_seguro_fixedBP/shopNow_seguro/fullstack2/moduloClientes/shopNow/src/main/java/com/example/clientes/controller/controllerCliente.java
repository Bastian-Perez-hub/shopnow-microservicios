package com.example.clientes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.clientes.model.modeloCliente;
import com.example.clientes.service.serviceCliente;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shopnow/cliente")
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
public class controllerCliente {

    @Autowired
    private serviceCliente serviceController;

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Retorna la lista completa de clientes registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay clientes registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<modeloCliente>>> listar() {
        List<modeloCliente> listaClientes = serviceController.findAll();
        if (listaClientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<modeloCliente>> clientes = listaClientes.stream()
            .map(cliente -> EntityModel.of(cliente,
                linkTo(methodOn(controllerCliente.class).buscar(cliente.getIdCliente())).withSelfRel(),
                linkTo(methodOn(controllerCliente.class).listar()).withRel("listar")))
            .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(clientes,
            linkTo(methodOn(controllerCliente.class).listar()).withSelfRel()));
    }

    @PostMapping
    @Operation(summary = "Crear un cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EntityModel<modeloCliente>> guardar(@RequestBody modeloCliente nuevoCliente) {
        modeloCliente nuevoModelo = serviceController.save(nuevoCliente);

        EntityModel<modeloCliente> resource = EntityModel.of(nuevoModelo,
            linkTo(methodOn(controllerCliente.class).buscar(nuevoModelo.getIdCliente())).withSelfRel(),
            linkTo(methodOn(controllerCliente.class).listar()).withRel("listar"),
            linkTo(methodOn(controllerCliente.class).eliminar(nuevoModelo.getIdCliente())).withRel("eliminar"));

        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna un cliente específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<EntityModel<modeloCliente>> buscar(
        @Parameter(description = "ID del cliente a buscar", required = true)
        @PathVariable Long id) {
        try {
            modeloCliente nuevoModelo = serviceController.findById(id);

            EntityModel<modeloCliente> resource = EntityModel.of(nuevoModelo,
                linkTo(methodOn(controllerCliente.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(controllerCliente.class).listar()).withRel("listar"),
                linkTo(methodOn(controllerCliente.class).eliminar(id)).withRel("eliminar"));

            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<EntityModel<modeloCliente>> actualizar(
        @Parameter(description = "ID del cliente a actualizar", required = true)
        @PathVariable Long id,
        @RequestBody modeloCliente nuevoCliente) {
        try {
            modeloCliente nuevoModelo = serviceController.findById(id);
            nuevoModelo.setRun(nuevoCliente.getRun());
            nuevoModelo.setCorreo(nuevoCliente.getCorreo());
            nuevoModelo.setNombre(nuevoCliente.getNombre());
            nuevoModelo.setFechaNacimiento(nuevoCliente.getFechaNacimiento());
            nuevoModelo.setApellido(nuevoCliente.getApellido());
            serviceController.save(nuevoModelo);

            EntityModel<modeloCliente> resource = EntityModel.of(nuevoModelo,
                linkTo(methodOn(controllerCliente.class).buscar(id)).withSelfRel(),
                linkTo(methodOn(controllerCliente.class).listar()).withRel("listar"),
                linkTo(methodOn(controllerCliente.class).eliminar(id)).withRel("eliminar"));

            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<?> eliminar(
        @Parameter(description = "ID del cliente a eliminar", required = true)
        @PathVariable Long id) {
        try {
            serviceController.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}