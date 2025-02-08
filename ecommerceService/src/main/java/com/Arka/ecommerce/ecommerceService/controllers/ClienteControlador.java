package com.Arka.ecommerce.ecommerceService.controllers;

import ch.qos.logback.core.net.server.Client;
import com.Arka.ecommerce.ecommerceService.DTOs.ClienteDto;
import com.Arka.ecommerce.ecommerceService.services.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteControlador {
    @Autowired
    private ClienteServicio clienteServicio;

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<ClienteDto>> obtenerTodosLosClientes() {
        return ResponseEntity.ok(clienteServicio.obtenerTodosLosClientes());
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerClientePorId(@PathVariable Long id) {
        ClienteDto cliente = clienteServicio.obtenerClientePorId(id);
        return (cliente != null) ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }
    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDTO) {
        ClienteDto nuevoCliente = clienteServicio.crearCliente(clienteDTO);
        return ResponseEntity.ok(nuevoCliente);
    }


    // Actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDto clienteDTO) {
        ClienteDto clienteActualizado = clienteServicio.actualizarCliente(id, clienteDTO);
        return (clienteActualizado != null) ? ResponseEntity.ok(clienteActualizado) : ResponseEntity.notFound().build();
    }
    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        boolean eliminado = clienteServicio.eliminarCliente(id);
        return (eliminado) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
