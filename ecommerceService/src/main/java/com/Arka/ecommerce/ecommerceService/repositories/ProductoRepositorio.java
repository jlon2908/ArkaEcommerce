package com.Arka.ecommerce.ecommerceService.repositories;

import com.Arka.ecommerce.ecommerceService.entities.Categoria;
import com.Arka.ecommerce.ecommerceService.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {


    // Buscar productos por categoría
    List<Producto> findByCategoria(Categoria categoria);
}
