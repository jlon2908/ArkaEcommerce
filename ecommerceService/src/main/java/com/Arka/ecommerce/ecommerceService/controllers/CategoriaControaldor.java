package com.Arka.ecommerce.ecommerceService.controllers;

import com.Arka.ecommerce.ecommerceService.entities.Categoria;
import com.Arka.ecommerce.ecommerceService.repositories.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaControaldor {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodasLasCategorias() {
        return ResponseEntity.ok(categoriaRepositorio.findAll());
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable  Long id) {
        Optional<Categoria> categoria = categoriaRepositorio.findById(id);
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria categoria) {
        Categoria nuevaCategoria = categoriaRepositorio.save(categoria);
        return ResponseEntity.ok(nuevaCategoria);
    }

    // Actualizar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Optional<Categoria> categoriaExistente = categoriaRepositorio.findById(id);
        if (categoriaExistente.isPresent()) {
            Categoria categoriaActualizada = categoriaExistente.get();
            categoriaActualizada.setNombre(categoria.getNombre());
            return ResponseEntity.ok(categoriaRepositorio.save(categoriaActualizada));
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        if (categoriaRepositorio.existsById(id)) {
            categoriaRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
