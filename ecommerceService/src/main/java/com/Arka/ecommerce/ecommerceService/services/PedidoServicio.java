package com.Arka.ecommerce.ecommerceService.services;

import com.Arka.ecommerce.ecommerceService.DTOs.PedidoDto;
import com.Arka.ecommerce.ecommerceService.entities.Cliente;
import com.Arka.ecommerce.ecommerceService.entities.Pedido;
import com.Arka.ecommerce.ecommerceService.entities.Producto;
import com.Arka.ecommerce.ecommerceService.repositories.ClienteRepositorio;
import com.Arka.ecommerce.ecommerceService.repositories.PedidoRepositorio;
import com.Arka.ecommerce.ecommerceService.repositories.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServicio {
    @Autowired
    private PedidoRepositorio pedidoRepositorio;


    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    // Obtener todos los pedidos
    public List<PedidoDto> obtenerTodosLosPedidos() {
        return pedidoRepositorio.findAll()
                .stream()
                .map(this::convertirAPedidoDTO)
                .collect(Collectors.toList());
    }

    public PedidoDto obtenerPedidoPorId(Long id) {
        Optional<Pedido> pedido = pedidoRepositorio.findById(id);
        return pedido.map(this::convertirAPedidoDTO).orElse(null);
    }
    // Crear un nuevo pedido
    public PedidoDto crearPedido(PedidoDto pedidoDTO) {
        Optional<Cliente> cliente = clienteRepositorio.findById(pedidoDTO.getClienteId());
        if (cliente.isPresent()) {
            Pedido pedido = new Pedido();
            pedido.setFecha(pedidoDTO.getFecha());
            pedido.setCliente(cliente.get());

            // Asociar productos al pedido
            List<Producto> productos = productoRepositorio.findAllById(pedidoDTO.getProductosIds());
            pedido.setProductos(productos);

            Pedido pedidoGuardado = pedidoRepositorio.save(pedido);
            return convertirAPedidoDTO(pedidoGuardado);
        }
        return null;
    }

    public PedidoDto actualizarPedido(Long id, PedidoDto pedidoDTO) {
        Optional<Pedido> pedidoExistente = pedidoRepositorio.findById(id);
        if (pedidoExistente.isPresent()) {
            Pedido pedido = pedidoExistente.get();
            pedido.setFecha(pedidoDTO.getFecha());

            List<Producto> productos = productoRepositorio.findAllById(pedidoDTO.getProductosIds());
            pedido.setProductos(productos);

            Pedido pedidoActualizado = pedidoRepositorio.save(pedido);
            return convertirAPedidoDTO(pedidoActualizado);
        }
        return null;
    }

    public boolean eliminarPedido(Long id) {
        if (pedidoRepositorio.existsById(id)) {
            pedidoRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PedidoDto> obtenerPedidosPorCliente(Long clienteId) {
        Optional<Cliente> cliente = clienteRepositorio.findById(clienteId);
        if (cliente.isPresent()) {
            return pedidoRepositorio.findByCliente(cliente.get())
                    .stream()
                    .map(this::convertirAPedidoDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public List<PedidoDto> obtenerPedidosEnRangoFechas(LocalDate inicio, LocalDate fin) {
        return pedidoRepositorio.findByFechaBetween(inicio, fin)
                .stream()
                .map(this::convertirAPedidoDTO)
                .collect(Collectors.toList());
    }

    // Buscar pedidos que contengan un producto específico
    public List<PedidoDto> obtenerPedidosPorProducto(Long productoId) {
        Optional<Producto> producto = productoRepositorio.findById(productoId);
        if (producto.isPresent()) {
            return pedidoRepositorio.findByProductosContaining(producto.get())
                    .stream()
                    .map(this::convertirAPedidoDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    private PedidoDto convertirAPedidoDTO(Pedido pedido) {
        List<Long> productosIds = pedido.getProductos()
                .stream()
                .map(Producto::getId)
                .collect(Collectors.toList());

        return new PedidoDto(pedido.getId(), pedido.getFecha(), pedido.getCliente().getId(), productosIds);
    }

}
