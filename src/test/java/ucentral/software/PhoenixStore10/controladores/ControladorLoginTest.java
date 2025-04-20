package ucentral.software.PhoenixStore10.controladores;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ucentral.software.PhoenixStore10.servicios.ServicioAutentificacion;
import ucentral.software.PhoenixStore10.servicios.ServicioUsuario;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladorLogin.class)
class ControladorLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioAutentificacion servicioAutentificacion;

    @MockBean
    private ServicioUsuario servicioUsuario; // aunque no se usa directamente, Spring lo necesita para construir el controlador

    @Test
    void mostrarFormularioLogin_DeberiaRetornarVistaLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    void iniciarSesion_ConCredencialesValidas_RolAdministrador() throws Exception {
        when(servicioAutentificacion.inicioSesion("admin", "1234")).thenReturn(true);
        when(servicioAutentificacion.definirRol("admin")).thenReturn("administrador");

        mockMvc.perform(post("/login")
                        .param("usuusername", "admin")
                        .param("usucontrasena", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("administrador"));
    }

    @Test
    void iniciarSesion_ConRolNoReconocido_DeberiaRetornarLoginConError() throws Exception {
        when(servicioAutentificacion.inicioSesion("test", "pass")).thenReturn(true);
        when(servicioAutentificacion.definirRol("test")).thenReturn("desconocido");

        mockMvc.perform(post("/login")
                        .param("usuusername", "test")
                        .param("usucontrasena", "pass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    void iniciarSesion_ConCredencialesInvalidas_DeberiaRetornarLoginConError() throws Exception {
        when(servicioAutentificacion.inicioSesion("user", "wrongpass")).thenReturn(false);

        mockMvc.perform(post("/login")
                        .param("usuusername", "user")
                        .param("usucontrasena", "wrongpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attributeExists("usuario"));
    }
}
