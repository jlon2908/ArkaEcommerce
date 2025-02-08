package com.Arka.ecommerce.ecommerceService.services;

import com.Arka.ecommerce.ecommerceService.DTOs.CarritoDto;
import com.Arka.ecommerce.ecommerceService.entities.Carrito;
import com.Arka.ecommerce.ecommerceService.entities.Cliente;
import com.Arka.ecommerce.ecommerceService.entities.Producto;
import com.Arka.ecommerce.ecommerceService.repositories.CarritoRepositorio;
import com.Arka.ecommerce.ecommerceService.repositories.ClienteRepositorio;
import com.Arka.ecommerce.ecommerceService.repositories.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoServicio {

    @Autowired
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    private CarritoDto convertirACarritoDTO(Carrito carrito) {
        List<Long> productosIds = carrito.getProductos()
                .stream()
                .map(Producto::getId)
                .collect(Collectors.toList());

        return new CarritoDto(
                carrito.getId(),
                carrito.getCliente().getId(),
                productosIds,
                carrito.getUltimaActualizacion()
        );
    }

    // Obtener todos los carritos
    public List<CarritoDto> obtenerTodosLosCarritos() {
        return carritoRepositorio.findAll()
                .stream()
                .map(this::convertirACarritoDTO)
                .collect(Collectors.toList());
    }

    // Obtener un carrito por ID
    public CarritoDto obtenerCarritoPorId(Long id) {
        Optional<Carrito> carrito = carritoRepositorio.findById(id);
        return carrito.map(this::convertirACarritoDTO).orElse(null);
    }

    // Crear un nuevo carrito
    public CarritoDto crearCarrito(CarritoDto carritoDTO) {
        Optional<Cliente> cliente = clienteRepositorio.findById(carritoDTO.getClienteId());
        if (cliente.isPresent()) {
            Carrito carrito = new Carrito();
            carrito.setCliente(cliente.get());
            carrito.setUltimaActualizacion(LocalDateTime.now());

            // Asociar productos al carrito
            List<Producto> productos = productoRepositorio.findAllById(carritoDTO.getProductosIds());
            carrito.setProductos(productos);

            Carrito carritoGuardado = carritoRepositorio.save(carrito);
            return convertirACarritoDTO(carritoGuardado);
        }
        return null;
    }


    // Actualizar un carrito
    public CarritoDto actualizarCarrito(Long id, CarritoDto carritoDTO) {
        Optional<Carrito> carritoExistente = carritoRepositorio.findById(id);
        Optional<Cliente> cliente = clienteRepositorio.findById(carritoDTO.getClienteId());

        if (carritoExistente.isPresent() && cliente.isPresent()) {
            Carrito carrito = carritoExistente.get();
            carrito.setUltimaActualizacion(LocalDateTime.now());

            // Asociar productos al carrito
            List<Producto> productos = productoRepositorio.findAllById(carritoDTO.getProductosIds());
            carrito.setProductos(productos);

            Carrito carritoActualizado = carritoRepositorio.save(carrito);
            return convertirACarritoDTO(carritoActualizado);
        }
        return null;
    }

    public boolean eliminarCarrito(Long id) {
        if (carritoRepositorio.existsById(id)) {
            carritoRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    // Obtener carritos abandonados (sin actividad en los últimos X días)
    public List<CarritoDto> obtenerCarritosAbandonados(int dias) {
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(dias);
        return carritoRepositorio.findByUltimaActualizacionBefore(fechaLimite)
                .stream()
                .map(this::convertirACarritoDTO)
                .collect(Collectors.toList());
    }
}
