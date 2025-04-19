package ucentral.software.PhoenixStore10.controladores;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.repositorios.RepoProducto;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladorCarrito.class)
public class ControladorCarritoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepoProducto productoRepositorio;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @Test
    void verCarrito_DeberiaMostrarVistaConTotalYCarrito() throws Exception {
        mockMvc.perform(get("/carrito").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("carrito"))
                .andExpect(model().attributeExists("carrito"))
                .andExpect(model().attributeExists("total"));
    }

    @Test
    void agregarAlCarrito_DeberiaAgregarYRedirigir() throws Exception {
        Productos producto = new Productos();
        producto.setId(1L);
        producto.setPronombre("Producto Test");
        producto.setProprecio(10.0);

        when(productoRepositorio.findById(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(post("/carrito/agregar")
                        .param("id", "1")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carrito"));
    }

    @Test
    void agregarAlCarrito_ProductoNoEncontrado_NoAgregaNada() throws Exception {
        when(productoRepositorio.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/carrito/agregar")
                        .param("id", "99")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carrito"));
    }
}
