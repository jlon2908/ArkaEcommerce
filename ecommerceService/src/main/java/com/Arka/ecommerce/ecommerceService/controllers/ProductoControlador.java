package com.Arka.ecommerce.ecommerceService.controllers;

import com.Arka.ecommerce.ecommerceService.DTOs.ProductoDto;
import com.Arka.ecommerce.ecommerceService.services.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoDto>> obtenerTodosLosProductos() {
        return ResponseEntity.ok(productoServicio.obtenerTodosLosProductos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtenerProductoPorId(@PathVariable Long id) {
        ProductoDto producto = productoServicio.obtenerProductoPorId(id);
        return (producto != null) ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
    }

    // Crear un nuevo producto
    @PostMapping
    public ResponseEntity<ProductoDto> crearProducto(@RequestBody ProductoDto productoDTO) {
        ProductoDto nuevoProducto = productoServicio.crearProducto(productoDTO);
        return (nuevoProducto != null) ? ResponseEntity.ok(nuevoProducto) : ResponseEntity.badRequest().build();
    }

    // Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDto productoDTO) {
        ProductoDto productoActualizado = productoServicio.actualizarProducto(id, productoDTO);
        return (productoActualizado != null) ? ResponseEntity.ok(productoActualizado) : ResponseEntity.notFound().build();
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoServicio.eliminarProducto(id);
        return (eliminado) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Obtener productos por categoría
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDto>> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        List<ProductoDto> productos = productoServicio.obtenerProductosPorCategoria(categoriaId);
        return (!productos.isEmpty()) ? ResponseEntity.ok(productos) : ResponseEntity.notFound().build();
    }
}
