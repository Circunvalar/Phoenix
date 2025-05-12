package ucentral.software.PhoenixStore10.controladores;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.servicios.ServicioProducto;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladorHome.class)
class ControladorHomeTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioProducto productoService;

    @Test
    void mostrarHome_SinQuery_DeberiaRetornarTodosLosProductos() throws Exception {
        Productos prod1 = new Productos();
        prod1.setPronombre("Producto 1");

        Productos prod2 = new Productos();
        prod2.setPronombre("Producto 2");

        List<Productos> productos = Arrays.asList(prod1, prod2);

        when(productoService.obtenerTodosLosProductos()).thenReturn(productos);

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("productos"));
    }

    @Test
    void mostrarHome_ConQuery_DeberiaBuscarProductosPorNombre() throws Exception {
        Productos prod = new Productos();
        prod.setPronombre("Camisa");

        List<Productos> productosFiltrados = List.of(prod);

        when(productoService.buscarPorNombre("camisa")).thenReturn(productosFiltrados);

        mockMvc.perform(get("/home").param("query", "camisa"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("productos"));
    }
}
