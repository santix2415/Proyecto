package com.Reservas.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Reservas.app.exception.NotFoundException;
import com.Reservas.app.entity.Cliente;
import com.Reservas.app.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ControllerRestCliente {
    
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Obtener todos los clientes.
     */
    @GetMapping("/clientes")
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Obtener un cliente por ID.
     */
    @GetMapping("/clientes/{id}")
    public Cliente getClienteById(@PathVariable String id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + id));
    }

    /**
     * Crear un nuevo cliente.
     */
    @PostMapping("/clientes")
    public Cliente saveCliente(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Cliente cliente = mapper.convertValue(body, Cliente.class);
        return clienteRepository.save(cliente);
    }

    /**
     * Actualizar un cliente existente.
     */
    @PutMapping("/clientes/{id}")
    public Cliente updateCliente(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Cliente cliente = mapper.convertValue(body, Cliente.class);
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    /**
     * Eliminar un cliente por ID.
     */
    @DeleteMapping("/clientes/{id}")
    public Cliente deleteCliente(@PathVariable String id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado con ID: " + id));
        clienteRepository.deleteById(id);
        return cliente;
    }
}