package ucentral.software.PhoenixStore10.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.servicios.ServicioProducto;

import java.util.List;

@Controller
public class ControladorHome {

    private final ServicioProducto productoService;

    public ControladorHome(ServicioProducto productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/home")
    public String mostrarHome(Model model, @RequestParam(required = false) String query) {
        List<Productos> productos;

        if (query != null && !query.trim().isEmpty()) {
            productos = productoService.buscarPorNombre(query);
        } else {
            productos = productoService.obtenerTodosLosProductos();
        }

        model.addAttribute("productos", productos);
        return "home";
    }
}