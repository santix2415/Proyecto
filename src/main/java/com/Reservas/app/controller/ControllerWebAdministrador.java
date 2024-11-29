package com.Reservas.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Reservas.app.entity.Administrador;
import com.Reservas.app.entity.Recepcionista;
import com.Reservas.app.exception.NotFoundException;
import com.Reservas.app.repository.AdministradorRepository;
import com.Reservas.app.repository.RecepcionistaRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "coordinador")
public class ControllerWebAdministrador {
    
    @Autowired
    private AdministradorRepository adminRepository;

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;
    
    @GetMapping("/index")
    public String adminIndexTemplate(Model model, HttpSession session) {
        // Obtener el usuario logeado de la sesión
    	Administrador admin = (Administrador) session.getAttribute("usuarioLogeado");
        
        // Verificar si el usuario está logeado antes de agregarlo al modelo
        if (admin != null) {
            model.addAttribute("usuario", admin.getUsuario());
            model.addAttribute("nombre", admin.getNombre());
        }
        
        return "index-reserva";
    }
    
    @GetMapping("/login")
    public String adminLoginTemplate(Model model) {
        return "login-admin";
    }
    
    public class HorarioController {

        @GetMapping("/horario")
        public String mostrarHorario(Model model) {
            return "horario"; // Asegúrate de que `horario.html` esté en `src/main/resources/templates` si usas Thymeleaf.
        }
    }
    
    @PostMapping("/logear")
    public String adminLogearTemplate(@RequestParam String usuario, @RequestParam String contrasena, Model model, HttpSession session) {
        // Buscar al coordinador por nombre de usuario en la base de datos
    	Administrador admin = null;
        for (Administrador c : adminRepository.findAll()) {
            if (c.getUsuario().equals(usuario)) {
                admin = c;
                break;
            }
        }
        
        // Verificar si se encontró al coordinador y si la contraseña es correcta
        if (admin != null && admin.getContrasena().equals(contrasena)) {
            // Guardar el usuario logeado en la sesión
            session.setAttribute("usuarioLogeado", admin);
            // Si las credenciales son correctas, redirigir a la página de inicio
            return "redirect:/coordinador/index";
        } else {
            // Si las credenciales son incorrectas, mostrar un mensaje de error y volver al formulario de login
            model.addAttribute("error", true);
            return "login-admin";
        }
    }
    
    @GetMapping("/estudiante/crear")
    public String adminCrearTemplate(Model model) {
		model.addAttribute("estudiante", new Recepcionista());
        return "estudiante-form";
    }
    
	
	@GetMapping("/lista")
	public String asociacionListTemplate(Model model) {
		model.addAttribute("estudiantes", recepcionistaRepository.findAll());
		return "estudiante-lista";
	}

	@GetMapping("/estudiante/edit/{id}")
	public String adminEditTemplate(@PathVariable("id") String id, Model model) {
		model.addAttribute("estudiante",
				recepcionistaRepository.findById(id).orElseThrow(() -> new NotFoundException("estudiante no encontrada")));
		return "estudiante-form";
	}

	@PostMapping("/estudiante/save")
	public String adminSaveProcess(@ModelAttribute("estudiante") Recepcionista recepcionista) {
		if (recepcionista.getId().isEmpty()) {
			recepcionista.setId(null);
		}
		recepcionistaRepository.save(recepcionista);
		return "redirect:/coordinador/index";
	}

	@GetMapping("/estudiante/delete/{id}")
	public String adminDeleteProcess(@PathVariable("id") String id) {
		recepcionistaRepository.deleteById(id);
		return "redirect:/coordinador/lista";
	}
}