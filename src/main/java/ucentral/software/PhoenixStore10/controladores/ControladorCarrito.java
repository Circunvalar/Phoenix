package ucentral.software.PhoenixStore10.controladores;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ucentral.software.PhoenixStore10.entidades.*;
import ucentral.software.PhoenixStore10.repositorios.RepoProducto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class ControladorCarrito {

    private final RepoProducto productoRepositorio;

    public ControladorCarrito(RepoProducto productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    @GetMapping
    public String verCarrito(HttpSession session, Model model) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        double total = carrito.stream()
                .mapToDouble(item -> item.getPrecio() * item.getCantidad())
                .sum();

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "carrito"; // carrito.html
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam("id") Long id, HttpSession session) {
        Optional<Productos> productoOpt = productoRepositorio.findById(id);

        if (productoOpt.isPresent()) {
            Productos producto = productoOpt.get();

            List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            // Buscar si ya está en el carrito
            boolean encontrado = false;
            for (ItemCarrito item : carrito) {
                if (item.getProductoId().equals(producto.getId())) {
                    item.setCantidad(item.getCantidad() + 1);
                    encontrado = true;
                    break;
                }
            }

            // Si no está, agregar nuevo
            if (!encontrado) {
                carrito.add(new ItemCarrito(producto.getId(), producto.getPronombre(), producto.getProprecio(), 1));
            }

            session.setAttribute("carrito", carrito);
        }

        return "redirect:/carrito";
    }
}
