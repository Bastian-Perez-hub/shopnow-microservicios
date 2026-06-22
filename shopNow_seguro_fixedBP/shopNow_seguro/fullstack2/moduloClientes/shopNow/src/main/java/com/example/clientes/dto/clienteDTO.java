package com.example.clientes.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class clienteDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String run;
    private Date fechaNacimiento;
}
