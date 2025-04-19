package ucentral.software.PhoenixStore10.controladores;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.servicios.ServicioUsuario;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladorRegistro.class)
public class ControladorRegistroTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioUsuario servicioUsuario;

    @Test
    void formularioRegistro_DeberiaMostrarVistaRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    void registrarUsuario_Exitosamente_DeberiaRedirigirALogin() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsuusername("nuevo");
        usuario.setUsucontrasena("1234");
        usuario.setUsucedula(123456L);

        when(servicioUsuario.registrarUsuario(
                org.mockito.ArgumentMatchers.any(Usuario.class),
                org.mockito.ArgumentMatchers.any(Model.class)))
                .thenReturn("redirect:/login");

        mockMvc.perform(post("/register")
                        .param("usuusername", "nuevo")
                        .param("usucontrasena", "1234")
                        .param("usucedula", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
