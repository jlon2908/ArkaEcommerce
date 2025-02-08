package com.Arka.ecommerce.ecommerceService.controllers;

import com.Arka.ecommerce.ecommerceService.entities.Proveedor;
import com.Arka.ecommerce.ecommerceService.repositories.ProvedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proveedores")
public class ProvedorControlador {

    @Autowired
    private ProvedorRepositorio proveedorRepositorio;

    // Obtener todos los proveedores
    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodosLosProveedores() {
        return ResponseEntity.ok(proveedorRepositorio.findAll());
    }

    // Obtener un proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
        Optional<Proveedor> proveedor = proveedorRepositorio.findById(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo proveedor
    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor) {
        Proveedor nuevoProveedor = proveedorRepositorio.save(proveedor);
        return ResponseEntity.ok(nuevoProveedor);
    }

    // Actualizar un proveedor
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Optional<Proveedor> proveedorExistente = proveedorRepositorio.findById(id);
        if (proveedorExistente.isPresent()) {
            Proveedor proveedorActualizado = proveedorExistente.get();
            proveedorActualizado.setNombre(proveedor.getNombre());
            proveedorActualizado.setEmail(proveedor.getEmail());
            proveedorActualizado.setTelefono(proveedor.getTelefono());
            return ResponseEntity.ok(proveedorRepositorio.save(proveedorActualizado));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        if (proveedorRepositorio.existsById(id)) {
            proveedorRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
