package ucentral.software.PhoenixStore10.controladores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import ucentral.software.PhoenixStore10.servicios.ServicioProducto;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ControladorInicio.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ControladorInicioDiffblueTest {
    @Autowired
    private ControladorInicio controladorInicio;

    @MockitoBean
    private ServicioProducto servicioProducto;

    /**
     * Test {@link ControladorInicio#mostrarTienda()}.
     * <p>
     * Method under test: {@link ControladorInicio#mostrarTienda()}
     */
    @Test
    @DisplayName("Test mostrarTienda()")
    @Tag("MaintainedByDiffblue")
    void testMostrarTienda() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tienda");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(controladorInicio)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("home"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("home"));
    }

    /**
     * Test {@link ControladorInicio#mostrarProductos(Model)}.
     * <p>
     * Method under test: {@link ControladorInicio#mostrarProductos(Model)}
     */
    @Test
    @DisplayName("Test mostrarProductos(Model)")
    @Tag("MaintainedByDiffblue")
    void testMostrarProductos() throws Exception {
        // Arrange
        when(servicioProducto.obtenerTodos()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(controladorInicio)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    /**
     * Test {@link ControladorInicio#buscarProductos(String, Model)}.
     * <p>
     * Method under test: {@link ControladorInicio#buscarProductos(String, Model)}
     */
    @Test
    @DisplayName("Test buscarProductos(String, Model)")
    @Tag("MaintainedByDiffblue")
    void testBuscarProductos() throws Exception {
        // Arrange
        when(servicioProducto.buscarPorNombre(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/buscar").param("query", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(controladorInicio)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("productos"))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }
}
