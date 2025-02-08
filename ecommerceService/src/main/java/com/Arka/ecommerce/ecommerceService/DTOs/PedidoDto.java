package com.Arka.ecommerce.ecommerceService.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {
    private Long id;
    private LocalDate fecha;
    private Long clienteId; // Relación con Cliente
    private List<Long> productosIds; // Lista de productos en el pedido
}
