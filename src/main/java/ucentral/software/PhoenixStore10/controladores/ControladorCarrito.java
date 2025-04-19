package ucentral.software.PhoenixStore10.controladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.ai.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.servicios.ServicioProducto;
import ucentral.software.PhoenixStore10.entidades.*;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class ControladorCarrito {

    @Autowired
    private ServicioProducto productoServicio;

    // Mostrar carrito
    @GetMapping
    public String mostrarCarrito(HttpSession session, Model model) {
        Map<Long, ItemCarrito> carrito = obtenerCarritoDesdeSesion(session);

        List<ItemCarrito> items = new ArrayList<>(carrito.values());
        double total = items.stream()
                .mapToDouble(item -> item.getProducto().getProprecio() * item.getCantidad())
                .sum();

        model.addAttribute("carrito", items);
        model.addAttribute("total", total);



        return "carrito";
    }

    // Agregar producto
    @PostMapping("/add")
    public String agregarAlCarrito(@RequestParam Long productoId, HttpSession session) {
        Productos producto = productoServicio.obtenerPorId(productoId);
        if (producto != null) {
            Map<Long, ItemCarrito> carrito = obtenerCarritoDesdeSesion(session);
            carrito.compute(productoId, (id, item) -> {
                if (item == null) return new ItemCarrito(producto, 1);
                item.incrementarCantidad();
                return item;
            });
        }
        return "redirect:/cart";
    }

    // Eliminar producto
    @PostMapping("/remove")
    public String eliminarDelCarrito(@RequestParam Long productoId, HttpSession session) {
        Map<Long, ItemCarrito> carrito = obtenerCarritoDesdeSesion(session);
        carrito.remove(productoId);
        return "redirect:/cart";
    }

    // Obtener carrito desde la sesión (si no existe, lo crea)
    @SuppressWarnings("unchecked")
    private Map<Long, ItemCarrito> obtenerCarritoDesdeSesion(HttpSession session) {
        Map<Long, ItemCarrito> carrito = (Map<Long, ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new HashMap<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }
}
