package com.Arka.ecommerce.ecommerceService.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long categoriaId; // Relación con Categoría
    private Long proveedorId; // Relación con Proveedor
}
