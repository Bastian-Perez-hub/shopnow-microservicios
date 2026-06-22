package com.example.moduloPedidos.service;

import com.example.moduloPedidos.dto.pedidoDTO;
import com.example.moduloPedidos.model.modeloPedido;

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
    public void procesarCarga(List<pedidoDTO> listaDto) {
        int batchSize = 50;

        for (int i = 0; i < listaDto.size(); i++) {
            pedidoDTO dto = listaDto.get(i);

            modeloPedido pedidoNuevo = new modeloPedido();
            pedidoNuevo.setFechaPedido(dto.getFechaPedido());
            pedidoNuevo.setEstado(dto.getEstado());
            pedidoNuevo.setTotal(dto.getTotal());
            pedidoNuevo.setMetodoPago(dto.getMetodoPago());
            pedidoNuevo.setDireccionEnvio(dto.getDireccionEnvio());
            pedidoNuevo.setFechaEntrega(dto.getFechaEntrega());
            pedidoNuevo.setIdCliente(dto.getIdCliente());

            entityManager.persist(pedidoNuevo);

            // Cada 50 registros, enviamos a la BD y limpiamos RAM
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
