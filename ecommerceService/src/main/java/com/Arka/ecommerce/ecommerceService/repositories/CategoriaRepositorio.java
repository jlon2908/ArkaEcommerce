package com.Arka.ecommerce.ecommerceService.repositories;

import com.Arka.ecommerce.ecommerceService.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

}
