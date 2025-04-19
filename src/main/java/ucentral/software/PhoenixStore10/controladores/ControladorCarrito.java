package ucentral.software.PhoenixStore10.controladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ucentral.software.PhoenixStore10.entidades.ItemCarrito;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.servicios.ServicioProducto;

import java.util.*;

@Controller
@RequestMapping("/carrito")
public class ControladorCarrito {

    @Autowired
    private ServicioProducto productoServicio;

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        Map<Integer, ItemCarrito> carrito = (Map<Integer, ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) carrito = new HashMap<>();

        double total = carrito.values().stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();

        model.addAttribute("carrito", carrito.values());
        model.addAttribute("total", total);

        return "carrito";
    }

    @PostMapping("/agregar/{id}")
    public String agregarAlCarrito(@PathVariable int id, HttpSession session) {
        Map<Integer, ItemCarrito> carrito = (Map<Integer, ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) carrito = new HashMap<>();

        Productos producto = productoServicio.obtenerPorId(id);
        if (producto != null) {
            ItemCarrito item = carrito.get(id);
            if (item == null) {
                carrito.put(id, new ItemCarrito(producto, 1));
            } else {
                item.incrementarCantidad();
            }
        }

        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarDelCarrito(@PathVariable int id, HttpSession session) {
        Map<Integer, ItemCarrito> carrito = (Map<Integer, ItemCarrito>) session.getAttribute("carrito");
        if (carrito != null) {
            carrito.remove(id);
        }
        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }
}
