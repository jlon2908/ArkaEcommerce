package com.Arka.ecommerce.ecommerceService.repositories;

import com.Arka.ecommerce.ecommerceService.entities.Cliente;
import com.Arka.ecommerce.ecommerceService.entities.Pedido;
import com.Arka.ecommerce.ecommerceService.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

    // Buscar pedidos que contengan un producto específico
    List<Pedido> findByProductosContaining(Producto producto);

    // Buscar pedidos dentro de un rango de fechas
    List<Pedido> findByFechaBetween(LocalDate inicio, LocalDate fin);

    // Historial de pedidos de un cliente
    List<Pedido> findByCliente(Cliente cliente);


}
