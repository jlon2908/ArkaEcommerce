package com.Arka.ecommerce.ecommerceService.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDto {
    private Long id;
    private Long clienteId; // Relación con Cliente
    private List<Long> productosIds; // Lista de productos en el carrito
    private LocalDateTime ultimaActualizacion;
}
