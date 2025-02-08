package com.Arka.ecommerce.ecommerceService.controllers;

import com.Arka.ecommerce.ecommerceService.DTOs.CarritoDto;
import com.Arka.ecommerce.ecommerceService.services.CarritoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos")
public class CarritoControlador {
    @Autowired
    private CarritoServicio carritoServicio;



    // Obtener todos los carritos
    @GetMapping
    public ResponseEntity<List<CarritoDto>> obtenerTodosLosCarritos() {
        return ResponseEntity.ok(carritoServicio.obtenerTodosLosCarritos());
    }

    // Obtener un carrito por ID
    @GetMapping("/{id}")
    public ResponseEntity<CarritoDto> obtenerCarritoPorId(@PathVariable Long id) {
        CarritoDto carrito = carritoServicio.obtenerCarritoPorId(id);
        return (carrito != null) ? ResponseEntity.ok(carrito) : ResponseEntity.notFound().build();
    }

    // Crear un nuevo carrito
    @PostMapping
    public ResponseEntity<CarritoDto> crearCarrito(@RequestBody CarritoDto carritoDTO) {
        CarritoDto nuevoCarrito = carritoServicio.crearCarrito(carritoDTO);
        return (nuevoCarrito != null) ? ResponseEntity.ok(nuevoCarrito) : ResponseEntity.badRequest().build();
    }

    // Actualizar un carrito
    @PutMapping("/{id}")
    public ResponseEntity<CarritoDto> actualizarCarrito(@PathVariable Long id, @RequestBody CarritoDto carritoDTO) {
        CarritoDto carritoActualizado = carritoServicio.actualizarCarrito(id, carritoDTO);
        return (carritoActualizado != null) ? ResponseEntity.ok(carritoActualizado) : ResponseEntity.notFound().build();
    }

    // Eliminar un carrito
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long id) {
        boolean eliminado = carritoServicio.eliminarCarrito(id);
        return (eliminado) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Obtener carritos abandonados (sin actividad en los últimos X días)
    @GetMapping("/abandonados/{dias}")
    public ResponseEntity<List<CarritoDto>> obtenerCarritosAbandonados(@PathVariable int dias) {
        List<CarritoDto> carritos = carritoServicio.obtenerCarritosAbandonados(dias);
        return (!carritos.isEmpty()) ? ResponseEntity.ok(carritos) : ResponseEntity.notFound().build();
    }
}
