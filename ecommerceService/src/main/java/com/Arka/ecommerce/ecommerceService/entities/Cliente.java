package com.Arka.ecommerce.ecommerceService.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false,unique = true, length = 100)
    private String email;

    //Relacion 1: N con pedido(un cliente puede tener muchos pedidos)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Pedido> pedidos;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Carrito carrito;
}
