package com.example.moduloPedidos.service;

import com.example.moduloPedidos.model.modeloPedido;
import com.example.moduloPedidos.repository.repositoryPedido;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Transactional
public class servicePedido {
    @Autowired
    private repositoryPedido serviceRepository;

    public List<modeloPedido> findAll() {
        return serviceRepository.findAll();
    }

    public modeloPedido findById(Long id) {
        return serviceRepository.findById(id).get();
    }

    public modeloPedido save(modeloPedido nuevoPedido) {
        return serviceRepository.save(nuevoPedido);
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
