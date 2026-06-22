package com.example.clientes.service;

import com.example.clientes.model.modeloCliente;
import com.example.clientes.repository.repositoryCliente;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class serviceCliente {
    @Autowired
    private repositoryCliente serviceRepository;

    public List<modeloCliente> findAll() {
        return serviceRepository.findAll();
    }

    public modeloCliente findById(Long id) {
        return serviceRepository.findById(id).get();

    }

    public modeloCliente save(modeloCliente nuevoCliente) {
        return serviceRepository.save(nuevoCliente);

    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }

}
