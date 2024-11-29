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

import com.Reservas.app.exception.NotFoundException;
import com.Reservas.app.entity.Administrador;
import com.Reservas.app.repository.AdministradorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ControllerRestAdministrador {
	@Autowired
	private AdministradorRepository administradorRepository;

	@GetMapping("/")
	public List<Administrador> getAllAsociacions() {
		return administradorRepository.findAll();

	}

	@GetMapping("/{id}")
	public Administrador getAdministradorrById(@PathVariable String id) {
		return administradorRepository.findById(id).orElseThrow(() -> new NotFoundException("Coordinador no encontrada"));
	}

	@PostMapping("/")
	public Administrador saveAdministrador(@RequestBody Map<String, Object> body) {
		ObjectMapper mapper = new ObjectMapper();
		Administrador asociacion = mapper.convertValue(body, Administrador.class);
		return administradorRepository.save(asociacion);
	}

	@PutMapping("/{id}")
	public Administrador updateAdministrador(@PathVariable String id, @RequestBody Map<String, Object> body) {
		ObjectMapper mapper = new ObjectMapper();
		Administrador asociacion = mapper.convertValue(body, Administrador.class);
		asociacion.setId(id);
		return administradorRepository.save(asociacion);
	}

	@DeleteMapping("/{id}")
	public Administrador deleteAdministrador(@PathVariable String id) {
		Administrador asociacion = administradorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Club no encontrado"));
		administradorRepository.deleteById(id);
		return asociacion;
	}
}
