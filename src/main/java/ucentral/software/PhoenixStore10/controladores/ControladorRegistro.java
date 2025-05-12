package ucentral.software.PhoenixStore10.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.servicios.ServicioUsuario;

@Controller
public class ControladorRegistro {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/register")
    public String formularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            return servicioUsuario.registrarUsuario(usuario, model);
        } catch (Exception ex) {
            model.addAttribute("error", "Ocurrió un error durante el registro: " + ex.getMessage());
            return "register";
        }
    }
}
