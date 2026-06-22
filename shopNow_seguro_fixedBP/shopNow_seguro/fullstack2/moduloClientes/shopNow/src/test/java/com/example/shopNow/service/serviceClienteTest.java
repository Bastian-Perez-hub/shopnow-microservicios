package com.example.shopNow.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import com.example.clientes.model.modeloCliente;
import com.example.clientes.repository.repositoryCliente;
import com.example.clientes.service.serviceCliente;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class serviceClienteTest {

    @Mock
    private repositoryCliente serviceRepository;

    @InjectMocks
    private serviceCliente serviceController;

    @Test
    void testFindAll() {
        // datos falsos que el repositorio va a devolver
        List<modeloCliente> listaFalsa = new ArrayList<>();
        listaFalsa.add(new modeloCliente());
        listaFalsa.add(new modeloCliente());

        // le decimos al repositorio falso qué devolver
        when(serviceRepository.findAll()).thenReturn(listaFalsa);

        // llamamos al método real
        List<modeloCliente> resultado = serviceController.findAll();

        // verificamos que devolvió 2 clientes
        assertEquals(2, resultado.size());
        System.out.println("Test Get ejecutado con exito, resultado: " + resultado.size() + " clientes encontrados");
    }

    @Test
    void testFindById() {
        // cliente falso que el repositorio va a devolver
        modeloCliente clienteFalso = new modeloCliente();
        clienteFalso.setIdCliente(1L);
        clienteFalso.setNombre("Juan");

        // le decimos al repositorio falso qué devolver cuando busquen por id 1
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(clienteFalso));

        // llamamos al método real
        modeloCliente resultado = serviceController.findById(1L);

        // verificamos que el nombre es correcto
        assertEquals("Juan", resultado.getNombre());
        System.out.println("Test Get All ejecutado con exito  - cliente encontrado: " + resultado.getNombre());
    }

    @Test
    void testSave() {
        // cliente falso que el repositorio va a devolver
        modeloCliente clienteFalso = new modeloCliente();
        clienteFalso.setIdCliente(1L);
        clienteFalso.setNombre("Juan");

        // le decimos al repositorio falso qué devolver cuando guarden
        when(serviceRepository.save(clienteFalso)).thenReturn(clienteFalso);

        // llamamos al método real
        modeloCliente resultado = serviceController.save(clienteFalso);

        // verificamos que el nombre es correcto
        assertEquals("Juan", resultado.getNombre());
        System.out.println("Test save ejecutado con exito - cliente guardado: " + resultado.getNombre());
    }

    @Test
    void testUpdate() {
        // cliente original
        modeloCliente clienteOriginal = new modeloCliente();
        clienteOriginal.setIdCliente(1L);
        clienteOriginal.setNombre("Juan");

        // cliente con datos actualizados
        modeloCliente clienteActualizado = new modeloCliente();
        clienteActualizado.setIdCliente(1L);
        clienteActualizado.setNombre("Pedro");

        // simulamos findById y luego save
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(clienteOriginal));
        when(serviceRepository.save(clienteOriginal)).thenReturn(clienteActualizado);

        // llamamos a findById y luego save como hace el controller
        modeloCliente encontrado = serviceController.findById(1L);
        encontrado.setNombre("Pedro");
        modeloCliente resultado = serviceController.save(encontrado);

        // verificamos que el nombre fue actualizado
        assertEquals("Pedro", resultado.getNombre());
        System.out.println("Test update ejecutado con exito - cliente actualizado: " + resultado.getNombre());
    }

    @Test
    void testDelete() {
        // llamamos al método real
        serviceController.delete(1L);

        // verificamos que el repositorio llamó a deleteById con el id correcto
        Mockito.verify(serviceRepository).deleteById(1L);
        System.out.println("Test delete ejecutado con exito - cliente eliminado con id: 1");
    }

}
