package com.example.moduloPedidos.controller;

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

import com.example.moduloPedidos.model.modeloPedido;
import com.example.moduloPedidos.service.servicePedido;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shopnow/pedido")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con la gestión de pedidos")
public class controllerPedido {

    @Autowired
    private servicePedido serviceController;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos", description = "Retorna la lista completa de pedidos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada exitosamente"),
            @ApiResponse(responseCode = "204", description = "No hay pedidos registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<modeloPedido>>> listar() {
        List<modeloPedido> listaPedidos = serviceController.findAll();
        if (listaPedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<modeloPedido>> pedidos = listaPedidos.stream()
                .map(pedido -> EntityModel.of(pedido,
                        linkTo(methodOn(controllerPedido.class).buscar(pedido.getIdPedido())).withSelfRel(),
                        linkTo(methodOn(controllerPedido.class).listar()).withRel("listar")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(pedidos,
                linkTo(methodOn(controllerPedido.class).listar()).withSelfRel()));
    }

    @PostMapping
    @Operation(summary = "Crear un pedido", description = "Registra un nuevo pedido en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<EntityModel<modeloPedido>> guardar(@RequestBody modeloPedido nuevoPedido) {
        modeloPedido nuevoModelo = serviceController.save(nuevoPedido);

        EntityModel<modeloPedido> resource = EntityModel.of(nuevoModelo,
                linkTo(methodOn(controllerPedido.class).buscar(nuevoModelo.getIdPedido())).withSelfRel(),
                linkTo(methodOn(controllerPedido.class).listar()).withRel("listar"),
                linkTo(methodOn(controllerPedido.class).eliminar(nuevoModelo.getIdPedido())).withRel("eliminar"));

        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna un pedido específico según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<EntityModel<modeloPedido>> buscar(
            @Parameter(description = "ID del pedido a buscar", required = true) @PathVariable Long id) {
        try {
            modeloPedido nuevoModelo = serviceController.findById(id);

            EntityModel<modeloPedido> resource = EntityModel.of(nuevoModelo,
                    linkTo(methodOn(controllerPedido.class).buscar(id)).withSelfRel(),
                    linkTo(methodOn(controllerPedido.class).listar()).withRel("listar"),
                    linkTo(methodOn(controllerPedido.class).eliminar(id)).withRel("eliminar"));

            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza los datos de un pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<EntityModel<modeloPedido>> actualizar(
            @Parameter(description = "ID del pedido a actualizar", required = true) @PathVariable Long id,
            @RequestBody modeloPedido nuevoPedido) {
        try {
            modeloPedido nuevoModelo = serviceController.findById(id);
            nuevoModelo.setFechaPedido(nuevoPedido.getFechaPedido());
            nuevoModelo.setEstado(nuevoPedido.getEstado());
            nuevoModelo.setTotal(nuevoPedido.getTotal());
            nuevoModelo.setMetodoPago(nuevoPedido.getMetodoPago());
            nuevoModelo.setDireccionEnvio(nuevoPedido.getDireccionEnvio());
            nuevoModelo.setFechaEntrega(nuevoPedido.getFechaEntrega());
            serviceController.save(nuevoModelo);

            EntityModel<modeloPedido> resource = EntityModel.of(nuevoModelo,
                    linkTo(methodOn(controllerPedido.class).buscar(id)).withSelfRel(),
                    linkTo(methodOn(controllerPedido.class).listar()).withRel("listar"),
                    linkTo(methodOn(controllerPedido.class).eliminar(id)).withRel("eliminar"));

            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del pedido a eliminar", required = true) @PathVariable Long id) {
        try {
            serviceController.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}