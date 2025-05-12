package ucentral.software.PhoenixStore10.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.servicios.ServicioProducto;

import java.util.List;

@Controller
public class ControladorInicio {

    @Autowired
    private ServicioProducto servicioProducto;

    @GetMapping("/tienda")
    public String mostrarTienda() {
        return "home";
    }

    @GetMapping("/")
    public String mostrarProductos(Model model) {
        List<Productos> productos = servicioProducto.obtenerTodos();
        model.addAttribute("productos", productos);
        return "redirect:/login";
    }

    @GetMapping("/buscar")
    public String buscarProductos(@RequestParam("query") String query, Model model) {
        List<Productos> productos = servicioProducto.buscarPorNombre(query);
        model.addAttribute("productos", productos);
        return "index";
    }
}
