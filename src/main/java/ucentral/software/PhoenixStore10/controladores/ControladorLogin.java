package ucentral.software.PhoenixStore10.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.servicios.ServicioAutentificacion;
import ucentral.software.PhoenixStore10.servicios.ServicioUsuario;

@Controller
public class ControladorLogin {

    private final ServicioAutentificacion servicioAutentificacion;

    public ControladorLogin(ServicioUsuario servicioUsuario, ServicioAutentificacion servicioAutentificacion) {
        this.servicioAutentificacion = servicioAutentificacion;
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }


    @PostMapping("/login")
    public String iniciarSesion(Usuario usuario, Model model) {
        boolean loginExitoso = servicioAutentificacion.inicioSesion(usuario.getUsuusername(), usuario.getUsucontrasena());

        if (loginExitoso) {
            String rol = servicioAutentificacion.definirRol(usuario.getUsuusername());

            switch (rol) {
                case "administrador":
                    return "redirect:/administrador";
                case "cliente":
                    return "redirect:/home";
                case "trabajador":
                    return "redirect:/trabajador";
                case "repartidor":
                    return "redirect:/repartidor";
                default:
                    model.addAttribute("error", "Rol no reconocido");
                    model.addAttribute("usuario", usuario);
                    return "login";
            }
        } else {
            System.out.println("Falló el login");
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            model.addAttribute("usuario", usuario);
            return "redirect:/";
        }
    }

}

