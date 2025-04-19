package ucentral.software.PhoenixStore10.controladores;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.servicios.ServicioProducto;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladorInicio.class)
public class ControladorInicioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioProducto servicioProducto;

    @Test
    void mostrarTienda_debeRetornarVistaHome() throws Exception {
        mockMvc.perform(get("/tienda"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void mostrarProductos_debeObtenerProductosYRedirigirLogin() throws Exception {
        when(servicioProducto.obtenerTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void buscarProductos_debeRetornarVistaIndexConProductos() throws Exception {
        String query = "camisa";
        when(servicioProducto.buscarPorNombre(query)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/buscar").param("query", query))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("productos"));
    }
}
