package com.Arka.ecommerce.ecommerceService.repositories;

import com.Arka.ecommerce.ecommerceService.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepositorio  extends JpaRepository<Cliente,Long> {
    // Método personalizado para buscar un cliente por su email
    Optional<Cliente> findByEmail(String email);
}
