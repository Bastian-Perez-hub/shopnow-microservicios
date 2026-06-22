package com.example.clientes.model;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class modeloCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL usa Identity habitualmente
    private Long idCliente;

    private String nombre;
    private String apellido;
    private String correo;
    private String run;
    private Date fechaNacimiento;
}
