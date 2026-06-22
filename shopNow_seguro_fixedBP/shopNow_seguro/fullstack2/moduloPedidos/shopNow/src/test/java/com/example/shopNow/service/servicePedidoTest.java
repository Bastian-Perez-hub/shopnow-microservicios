package com.example.shopNow.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import com.example.moduloPedidos.model.modeloPedido;
import com.example.moduloPedidos.repository.repositoryPedido;
import com.example.moduloPedidos.service.servicePedido;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class servicePedidoTest {

    @Mock
    private repositoryPedido serviceRepository;

    @InjectMocks
    private servicePedido serviceController;

    @Test
    void testFindAll() {
        List<modeloPedido> listaFalsa = new ArrayList<>();
        listaFalsa.add(new modeloPedido());
        listaFalsa.add(new modeloPedido());

        when(serviceRepository.findAll()).thenReturn(listaFalsa);

        List<modeloPedido> resultado = serviceController.findAll();

        assertEquals(2, resultado.size());
        System.out.println("Test Get ejecutado con exito, resultado: " + resultado.size() + " pedidos encontrados");
    }

    @Test
    void testFindById() {
        modeloPedido pedidoFalso = new modeloPedido();
        pedidoFalso.setIdPedido(1L);
        pedidoFalso.setEstado("PENDIENTE");

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(pedidoFalso));

        modeloPedido resultado = serviceController.findById(1L);

        assertEquals("PENDIENTE", resultado.getEstado());
        System.out.println("Test FindById ejecutado con exito - pedido encontrado con estado: " + resultado.getEstado());
    }

    @Test
    void testSave() {
        modeloPedido pedidoFalso = new modeloPedido();
        pedidoFalso.setIdPedido(1L);
        pedidoFalso.setEstado("PENDIENTE");

        when(serviceRepository.save(pedidoFalso)).thenReturn(pedidoFalso);

        modeloPedido resultado = serviceController.save(pedidoFalso);

        assertEquals("PENDIENTE", resultado.getEstado());
        System.out.println("Test Save ejecutado con exito - pedido guardado con estado: " + resultado.getEstado());
    }

    @Test
    void testUpdate() {
        modeloPedido pedidoOriginal = new modeloPedido();
        pedidoOriginal.setIdPedido(1L);
        pedidoOriginal.setEstado("PENDIENTE");

        modeloPedido pedidoActualizado = new modeloPedido();
        pedidoActualizado.setIdPedido(1L);
        pedidoActualizado.setEstado("ENTREGADO");

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(pedidoOriginal));
        when(serviceRepository.save(pedidoOriginal)).thenReturn(pedidoActualizado);

        modeloPedido encontrado = serviceController.findById(1L);
        encontrado.setEstado("ENTREGADO");
        modeloPedido resultado = serviceController.save(encontrado);

        assertEquals("ENTREGADO", resultado.getEstado());
        System.out.println("Test Update ejecutado con exito - pedido actualizado con estado: " + resultado.getEstado());
    }

    @Test
    void testDelete() {
        serviceController.delete(1L);

        Mockito.verify(serviceRepository).deleteById(1L);
        System.out.println("Test Delete ejecutado con exito - pedido eliminado con id: 1");
    }
}