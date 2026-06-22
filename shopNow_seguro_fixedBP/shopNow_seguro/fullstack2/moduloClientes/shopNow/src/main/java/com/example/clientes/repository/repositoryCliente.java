package com.example.clientes.repository;

import com.example.clientes.model.modeloCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repositoryCliente extends JpaRepository<modeloCliente, Long> {
}
