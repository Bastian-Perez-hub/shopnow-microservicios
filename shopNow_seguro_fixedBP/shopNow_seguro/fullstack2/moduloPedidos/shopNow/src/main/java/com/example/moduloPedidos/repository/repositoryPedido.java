package com.example.moduloPedidos.repository;

import com.example.moduloPedidos.model.modeloPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repositoryPedido extends JpaRepository<modeloPedido, Long> {
}
