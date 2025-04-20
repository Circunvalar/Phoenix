package ucentral.software.PhoenixStore10.controladores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ucentral.software.PhoenixStore10.entidades.Productos;
import ucentral.software.PhoenixStore10.repositorios.RepoProducto;

@ContextConfiguration(classes = {ControladorCarrito.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ControladorCarritoDiffblueTest {
    @Autowired
    private ControladorCarrito controladorCarrito;

    @MockitoBean
    private RepoProducto repoProducto;

    /**
     * Test {@link ControladorCarrito#verCarrito(HttpSession, Model)}.
     * <ul>
     *   <li>Given {@link ArrayList#ArrayList()}.</li>
     *   <li>Then {@link ConcurrentModel#ConcurrentModel()} {@code carrito} is {@link ArrayList#ArrayList()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorCarrito#verCarrito(HttpSession, Model)}
     */
    @Test
    @DisplayName("Test verCarrito(HttpSession, Model); given ArrayList(); then ConcurrentModel() 'carrito' is ArrayList()")
    @Tag("MaintainedByDiffblue")
    void testVerCarrito_givenArrayList_thenConcurrentModelCarritoIsArrayList() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        ControladorCarrito controladorCarrito = new ControladorCarrito(mock(RepoProducto.class));
        HttpSession session = mock(HttpSession.class);
        ArrayList<Object> objectList = new ArrayList<>();
        when(session.getAttribute(Mockito.<String>any())).thenReturn(objectList);
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualVerCarritoResult = controladorCarrito.verCarrito(session, model);

        // Assert
        verify(session).getAttribute(eq("carrito"));
        assertEquals(2, model.size());
        Object getResult = model.get("carrito");
        assertTrue(getResult instanceof List);
        assertEquals("carrito", actualVerCarritoResult);
        assertEquals(0.0d, ((Double) model.get("total")).doubleValue());
        assertTrue(((List<Object>) getResult).isEmpty());
        assertSame(objectList, getResult);
    }

    /**
     * Test {@link ControladorCarrito#verCarrito(HttpSession, Model)}.
     * <ul>
     *   <li>When {@link MockHttpSession#MockHttpSession()}.</li>
     *   <li>Then {@link ConcurrentModel#ConcurrentModel()} size is two.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorCarrito#verCarrito(HttpSession, Model)}
     */
    @Test
    @DisplayName("Test verCarrito(HttpSession, Model); when MockHttpSession(); then ConcurrentModel() size is two")
    @Tag("MaintainedByDiffblue")
    void testVerCarrito_whenMockHttpSession_thenConcurrentModelSizeIsTwo() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        ControladorCarrito controladorCarrito = new ControladorCarrito(mock(RepoProducto.class));
        MockHttpSession session = new MockHttpSession();
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualVerCarritoResult = controladorCarrito.verCarrito(session, model);

        // Assert
        assertEquals(2, model.size());
        Object getResult = model.get("carrito");
        assertTrue(getResult instanceof List);
        assertEquals("carrito", actualVerCarritoResult);
        assertEquals(0.0d, ((Double) model.get("total")).doubleValue());
        assertTrue(((List<Object>) getResult).isEmpty());
    }

    /**
     * Test {@link ControladorCarrito#agregarAlCarrito(Long, HttpSession)}.
     * <ul>
     *   <li>Given {@link Productos#Productos()} Id is one.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorCarrito#agregarAlCarrito(Long, HttpSession)}
     */
    @Test
    @DisplayName("Test agregarAlCarrito(Long, HttpSession); given Productos() Id is one")
    @Tag("MaintainedByDiffblue")
    void testAgregarAlCarrito_givenProductosIdIsOne() throws Exception {
        // Arrange
        Productos productos = new Productos();
        productos.setId(1L);
        productos.setProcategoria("Procategoria");
        productos.setProcodigo(1L);
        productos.setProdescripcion("Prodescripcion");
        productos.setProid(1L);
        productos.setProimagenUrl("https://example.org/example");
        productos.setPronombre("Pronombre");
        productos.setProprecio(10.0d);
        Optional<Productos> ofResult = Optional.of(productos);
        when(repoProducto.findById(Mockito.<Long>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/carrito/agregar");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(controladorCarrito)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/carrito"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/carrito"));
    }

    /**
     * Test {@link ControladorCarrito#agregarAlCarrito(Long, HttpSession)}.
     * <ul>
     *   <li>Given {@link RepoProducto} {@link CrudRepository#findById(Object)} return empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorCarrito#agregarAlCarrito(Long, HttpSession)}
     */
    @Test
    @DisplayName("Test agregarAlCarrito(Long, HttpSession); given RepoProducto findById(Object) return empty")
    @Tag("MaintainedByDiffblue")
    void testAgregarAlCarrito_givenRepoProductoFindByIdReturnEmpty() throws Exception {
        // Arrange
        Optional<Productos> emptyResult = Optional.empty();
        when(repoProducto.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/carrito/agregar");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1L));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(controladorCarrito)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/carrito"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/carrito"));
    }
}
