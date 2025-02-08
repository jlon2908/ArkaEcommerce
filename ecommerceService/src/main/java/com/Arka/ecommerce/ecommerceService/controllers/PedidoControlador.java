package com.Arka.ecommerce.ecommerceService.controllers;

import com.Arka.ecommerce.ecommerceService.DTOs.PedidoDto;
import com.Arka.ecommerce.ecommerceService.services.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoControlador {
    @Autowired
    private PedidoServicio pedidoServicio;

    // Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<PedidoDto>> obtenerTodosLosPedidos() {
        return ResponseEntity.ok(pedidoServicio.obtenerTodosLosPedidos());
    }

    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> obtenerPedidoPorId(@PathVariable Long id) {
        PedidoDto pedido = pedidoServicio.obtenerPedidoPorId(id);
        return (pedido != null) ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }
    // Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<PedidoDto> crearPedido(@RequestBody PedidoDto pedidoDTO) {
        PedidoDto nuevoPedido = pedidoServicio.crearPedido(pedidoDTO);
        return (nuevoPedido != null) ? ResponseEntity.ok(nuevoPedido) : ResponseEntity.badRequest().build();
    }

    // Actualizar un pedido
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> actualizarPedido(@PathVariable Long id, @RequestBody PedidoDto pedidoDTO) {
        PedidoDto pedidoActualizado = pedidoServicio.actualizarPedido(id, pedidoDTO);
        return (pedidoActualizado != null) ? ResponseEntity.ok(pedidoActualizado) : ResponseEntity.notFound().build();
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        boolean eliminado = pedidoServicio.eliminarPedido(id);
        return (eliminado) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Obtener pedidos de un cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosPorCliente(@PathVariable Long clienteId) {
        List<PedidoDto> pedidos = pedidoServicio.obtenerPedidosPorCliente(clienteId);
        return (!pedidos.isEmpty()) ? ResponseEntity.ok(pedidos) : ResponseEntity.notFound().build();
    }

    // Obtener pedidos dentro de un rango de fechas
    @GetMapping("/fecha")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosEnRangoFechas(@RequestParam LocalDate inicio, @RequestParam LocalDate fin) {
        List<PedidoDto> pedidos = pedidoServicio.obtenerPedidosEnRangoFechas(inicio, fin);
        return (!pedidos.isEmpty()) ? ResponseEntity.ok(pedidos) : ResponseEntity.notFound().build();
    }

    // Obtener pedidos que contengan un producto específico
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<PedidoDto>> obtenerPedidosPorProducto(@PathVariable Long productoId) {
        List<PedidoDto> pedidos = pedidoServicio.obtenerPedidosPorProducto(productoId);
        return (!pedidos.isEmpty()) ? ResponseEntity.ok(pedidos) : ResponseEntity.notFound().build();
    }
}
