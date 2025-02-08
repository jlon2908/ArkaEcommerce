package com.Arka.ecommerce.ecommerceService.services;

import com.Arka.ecommerce.ecommerceService.DTOs.ProductoDto;
import com.Arka.ecommerce.ecommerceService.entities.Categoria;
import com.Arka.ecommerce.ecommerceService.entities.Producto;
import com.Arka.ecommerce.ecommerceService.entities.Proveedor;
import com.Arka.ecommerce.ecommerceService.repositories.CategoriaRepositorio;
import com.Arka.ecommerce.ecommerceService.repositories.ProductoRepositorio;
import com.Arka.ecommerce.ecommerceService.repositories.ProvedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServicio {
    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ProvedorRepositorio proveedorRepositorio;

    // Método para convertir de Producto a ProductoDTO
    private ProductoDto convertirAProductoDTO(Producto producto) {
        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getCategoria().getId(),
                producto.getProveedor().getId()
        );
    }

    // Obtener todos los productos
    public List<ProductoDto> obtenerTodosLosProductos() {
        return productoRepositorio.findAll()
                .stream()
                .map(this::convertirAProductoDTO)
                .collect(Collectors.toList());
    }

    // Obtener un producto por ID
    public ProductoDto obtenerProductoPorId(Long id) {
        Optional<Producto> producto = productoRepositorio.findById(id);
        return producto.map(this::convertirAProductoDTO).orElse(null);
    }

    public ProductoDto crearProducto(ProductoDto productoDTO) {
        Optional<Categoria> categoria = categoriaRepositorio.findById(productoDTO.getCategoriaId());
        Optional<Proveedor> proveedor = proveedorRepositorio.findById(productoDTO.getProveedorId());

        if (categoria.isPresent() && proveedor.isPresent()) {
            Producto producto = new Producto();
            producto.setNombre(productoDTO.getNombre());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setCategoria(categoria.get());
            producto.setProveedor(proveedor.get());

            Producto productoGuardado = productoRepositorio.save(producto);
            return convertirAProductoDTO(productoGuardado);
        }
        return null;
    }

    // Actualizar un producto
    public ProductoDto actualizarProducto(Long id, ProductoDto productoDTO) {
        Optional<Producto> productoExistente = productoRepositorio.findById(id);
        Optional<Categoria> categoria = categoriaRepositorio.findById(productoDTO.getCategoriaId());
        Optional<Proveedor> proveedor = proveedorRepositorio.findById(productoDTO.getProveedorId());

        if (productoExistente.isPresent() && categoria.isPresent() && proveedor.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNombre(productoDTO.getNombre());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setCategoria(categoria.get());
            producto.setProveedor(proveedor.get());

            Producto productoActualizado = productoRepositorio.save(producto);
            return convertirAProductoDTO(productoActualizado);
        }
        return null;
    }

    public boolean eliminarProducto(Long id) {
        if (productoRepositorio.existsById(id)) {
            productoRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ProductoDto> obtenerProductosPorCategoria(Long categoriaId) {
        Optional<Categoria> categoria = categoriaRepositorio.findById(categoriaId);
        if (categoria.isPresent()) {
            return productoRepositorio.findByCategoria(categoria.get())
                    .stream()
                    .map(this::convertirAProductoDTO)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

}
