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
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.servicios.ServicioUsuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ControladorRegistro.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ControladorRegistroDiffblueTest {
    @Autowired
    private ControladorRegistro controladorRegistro;

    @MockitoBean
    private ServicioUsuario servicioUsuario;

    /**
     * Test {@link ControladorRegistro#formularioRegistro(Model)}.
     * <p>
     * Method under test: {@link ControladorRegistro#formularioRegistro(Model)}
     */
    @Test
    @DisplayName("Test formularioRegistro(Model)")
    @Tag("MaintainedByDiffblue")
    void testFormularioRegistro() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        ControladorRegistro controladorRegistro = new ControladorRegistro();
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualFormularioRegistroResult = controladorRegistro.formularioRegistro(model);

        // Assert
        assertEquals(1, model.size());
        Object getResult = model.get("usuario");
        assertTrue(getResult instanceof Usuario);
        assertEquals("register", actualFormularioRegistroResult);
        assertNull(((Usuario) getResult).getUsucedula());
        assertNull(((Usuario) getResult).getUsuid());
        assertNull(((Usuario) getResult).getUsuapellidos());
        assertNull(((Usuario) getResult).getUsucontrasena());
        assertNull(((Usuario) getResult).getUsucorreo());
        assertNull(((Usuario) getResult).getUsudireccion());
        assertNull(((Usuario) getResult).getUsunombres());
        assertNull(((Usuario) getResult).getUsurol());
        assertNull(((Usuario) getResult).getUsutelefono());
        assertNull(((Usuario) getResult).getUsuusername());
    }

    /**
     * Test {@link ControladorRegistro#registrarUsuario(Usuario, Model)}.
     * <p>
     * Method under test: {@link ControladorRegistro#registrarUsuario(Usuario, Model)}
     */
    @Test
    @DisplayName("Test registrarUsuario(Usuario, Model)")
    @Tag("MaintainedByDiffblue")
    void testRegistrarUsuario() throws Exception {
        // Arrange
        when(servicioUsuario.registrarUsuario(Mockito.<Usuario>any(), Mockito.<Model>any()))
                .thenReturn("Registrar Usuario");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(controladorRegistro)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("usuario"))
                .andExpect(MockMvcResultMatchers.view().name("Registrar Usuario"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("Registrar Usuario"));
    }
}
