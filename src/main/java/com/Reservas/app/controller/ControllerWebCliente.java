package com.Reservas.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Reservas.app.entity.Cliente;
import com.Reservas.app.exception.NotFoundException;
import com.Reservas.app.repository.ClienteRepository;

@Controller
@RequestMapping(value = "cliente")
public class ControllerWebCliente {

    @Autowired
    private ClienteRepository clienteRepository;

    // Página de inicio para el cliente
    @GetMapping("/index")
    public String clienteIndexTemplate(Model model) {
        return "index-cliente"; // Página de inicio para el cliente
    }

    // Crear nueva reserva
    @GetMapping("/crear-reserva")
    public String crearReservaTemplate(Model model) {
        // Agrega cualquier lógica necesaria para preparar la vista
        return "crear-reserva"; // Vista para crear reservas
    }

    // Gestionar reservas
    @GetMapping("/gestionar-reservas")
    public String gestionarReservasTemplate(Model model) {
        // Aquí podrías cargar las reservas del cliente
        return "gestionar-reservas"; // Vista para gestionar reservas
    }

    // Estado de mesas
    @GetMapping("/estado-mesas")
    public String estadoMesasTemplate(Model model) {
        // Lógica opcional para obtener información sobre las mesas
        return "estado-mesas"; // Vista que muestra el estado de las mesas
    }

    // Horario
    @GetMapping("/horario")
    public String horarioTemplate(Model model) {
        // Lógica opcional para cargar horarios
        return "horario"; // Vista que muestra el horario del restaurante
    }

    // Crear nuevo cliente
    @GetMapping("/crear")
    public String clienteCrearTemplate(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente-form";
    }

    // Ver todos los clientes (solo para el administrador o el que gestione clientes)
    @GetMapping("/lista")
    public String clienteListTemplate(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "cliente-lista"; // Página que muestra todos los clientes
    }

    // Editar cliente
    @GetMapping("/edit/{id}")
    public String clienteEditTemplate(@PathVariable("id") String id, Model model) {
        model.addAttribute("cliente", 
            clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado")));
        return "cliente-form"; // Página para editar un cliente
    }

    // Guardar cliente (creación o edición)
    @PostMapping("/save")
    public String clienteSaveProcess(@ModelAttribute("cliente") Cliente cliente) {
        if (cliente.getId() == null || cliente.getId().isEmpty()) {
            cliente.setId(null); // Si el cliente no tiene ID, lo asignamos como null para crear un nuevo cliente
        }
        clienteRepository.save(cliente); // Guardamos el cliente en la base de datos
        return "redirect:/cliente/index"; // Redirigimos a la página principal del cliente
    }

    // Eliminar cliente
    @GetMapping("/delete/{id}")
    public String clienteDeleteProcess(@PathVariable("id") String id) {
        clienteRepository.deleteById(id); // Eliminamos el cliente por su ID
        return "redirect:/cliente/lista"; // Redirigimos a la lista de clientes
    }
}
