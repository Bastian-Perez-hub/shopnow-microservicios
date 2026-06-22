package com.example.moduloPedidos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class modeloPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    private Long idCliente;

    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal total;
    private String metodoPago;
    private String direccionEnvio;
    private LocalDate fechaEntrega;
}
