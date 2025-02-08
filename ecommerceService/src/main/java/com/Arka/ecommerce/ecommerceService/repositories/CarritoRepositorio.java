package com.Arka.ecommerce.ecommerceService.repositories;

import com.Arka.ecommerce.ecommerceService.entities.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarritoRepositorio extends JpaRepository<Carrito,Long> {
    // Obtener carritos abandonados (última actualización antes de una fecha límite)
    List<Carrito> findByUltimaActualizacionBefore(LocalDateTime fechaLimite);
}
