package com.example.moduloPedidos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class pedidoDTO {
    private long idCliente;
    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal total;
    private String metodoPago;
    private String direccionEnvio;
    private LocalDate fechaEntrega;
}
