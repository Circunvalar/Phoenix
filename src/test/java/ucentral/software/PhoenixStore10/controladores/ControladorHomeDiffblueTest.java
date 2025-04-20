package ucentral.software.PhoenixStore10.controladores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.servicios.ServicioProducto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ControladorHomeDiffblueTest {
    /**
     * Test {@link ControladorHome#mostrarHome(Model, String)}.
     * <ul>
     *   <li>Then calls {@link ServicioProducto#buscarPorNombre(String)}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorHome#mostrarHome(Model, String)}
     */
    @Test
    @DisplayName("Test mostrarHome(Model, String); then calls buscarPorNombre(String)")
    @Tag("MaintainedByDiffblue")
    void testMostrarHome_thenCallsBuscarPorNombre() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        ServicioProducto productoService = mock(ServicioProducto.class);
        ArrayList<Productos> productosList = new ArrayList<>();
        when(productoService.buscarPorNombre(Mockito.<String>any())).thenReturn(productosList);
        ControladorHome controladorHome = new ControladorHome(productoService);
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualMostrarHomeResult = controladorHome.mostrarHome(model, "Query");

        // Assert
        verify(productoService).buscarPorNombre(eq("Query"));
        assertEquals(1, model.size());
        Object getResult = model.get("productos");
        assertTrue(getResult instanceof List);
        assertEquals("home", actualMostrarHomeResult);
        assertTrue(((List<Object>) getResult).isEmpty());
        assertSame(productosList, getResult);
    }

    /**
     * Test {@link ControladorHome#mostrarHome(Model, String)}.
     * <ul>
     *   <li>Then calls {@link ServicioProducto#obtenerTodosLosProductos()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorHome#mostrarHome(Model, String)}
     */
    @Test
    @DisplayName("Test mostrarHome(Model, String); then calls obtenerTodosLosProductos()")
    @Tag("MaintainedByDiffblue")
    void testMostrarHome_thenCallsObtenerTodosLosProductos() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        ServicioProducto productoService = mock(ServicioProducto.class);
        ArrayList<Productos> productosList = new ArrayList<>();
        when(productoService.obtenerTodosLosProductos()).thenReturn(productosList);
        ControladorHome controladorHome = new ControladorHome(productoService);
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualMostrarHomeResult = controladorHome.mostrarHome(model, null);

        // Assert
        verify(productoService).obtenerTodosLosProductos();
        assertEquals(1, model.size());
        Object getResult = model.get("productos");
        assertTrue(getResult instanceof List);
        assertEquals("home", actualMostrarHomeResult);
        assertTrue(((List<Object>) getResult).isEmpty());
        assertSame(productosList, getResult);
    }

    /**
     * Test {@link ControladorHome#mostrarHome(Model, String)}.
     * <ul>
     *   <li>When empty string.</li>
     *   <li>Then calls {@link ServicioProducto#obtenerTodosLosProductos()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorHome#mostrarHome(Model, String)}
     */
    @Test
    @DisplayName("Test mostrarHome(Model, String); when empty string; then calls obtenerTodosLosProductos()")
    @Tag("MaintainedByDiffblue")
    void testMostrarHome_whenEmptyString_thenCallsObtenerTodosLosProductos() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        ServicioProducto productoService = mock(ServicioProducto.class);
        ArrayList<Productos> productosList = new ArrayList<>();
        when(productoService.obtenerTodosLosProductos()).thenReturn(productosList);
        ControladorHome controladorHome = new ControladorHome(productoService);
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualMostrarHomeResult = controladorHome.mostrarHome(model, "");

        // Assert
        verify(productoService).obtenerTodosLosProductos();
        assertEquals(1, model.size());
        Object getResult = model.get("productos");
        assertTrue(getResult instanceof List);
        assertEquals("home", actualMostrarHomeResult);
        assertTrue(((List<Object>) getResult).isEmpty());
        assertSame(productosList, getResult);
    }
}
