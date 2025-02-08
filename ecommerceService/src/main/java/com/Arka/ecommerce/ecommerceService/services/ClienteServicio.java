package com.Arka.ecommerce.ecommerceService.services;

import com.Arka.ecommerce.ecommerceService.DTOs.ClienteDto;
import com.Arka.ecommerce.ecommerceService.entities.Cliente;
import com.Arka.ecommerce.ecommerceService.repositories.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<ClienteDto> obtenerTodosLosClientes(){
        return clienteRepositorio.findAll()
                .stream()
                .map(this::convertirAClienteDTO)
                .collect(Collectors.toList());
    }

    public ClienteDto obtenerClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        return cliente.map(this::convertirAClienteDTO).orElse(null);
    }


    // Crear un nuevo cliente
    public ClienteDto crearCliente(ClienteDto clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        Cliente clienteGuardado = clienteRepositorio.save(cliente);
        return convertirAClienteDTO(clienteGuardado);
    }

    // Actualizar un cliente
    public ClienteDto actualizarCliente(Long id, ClienteDto clienteDTO) {
        Optional<Cliente> clienteExistente = clienteRepositorio.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente cliente = clienteExistente.get();
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setEmail(clienteDTO.getEmail());
            Cliente clienteActualizado = clienteRepositorio.save(cliente);
            return convertirAClienteDTO(clienteActualizado);
        }
        return null;
    }

    // Eliminar un cliente
    public boolean eliminarCliente(Long id) {
        if (clienteRepositorio.existsById(id)) {
            clienteRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    // Método para convertir de Cliente a ClienteDTO
    private ClienteDto convertirAClienteDTO(Cliente cliente) {
        return new ClienteDto(cliente.getId(), cliente.getNombre(), cliente.getEmail());
    }
}
