package com.example.clientes.service;

import com.example.clientes.dto.clienteDTO;
import com.example.clientes.model.modeloCliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CargaMasivaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void procesarCarga(List<clienteDTO> listaDto) {
        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {
            clienteDTO dto = listaDto.get(i);

            modeloCliente clienteNuevo = new modeloCliente();
            clienteNuevo.setCorreo(dto.getCorreo());
            clienteNuevo.setNombre(dto.getNombre());
            clienteNuevo.setFechaNacimiento(dto.getFechaNacimiento());
            clienteNuevo.setApellido(dto.getApellido());
            clienteNuevo.setRun(dto.getRun());

            entityManager.persist(clienteNuevo);

            // Cada 50 registros, enviamos a la BD y limpiamos RAM
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
