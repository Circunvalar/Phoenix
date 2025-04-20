package ucentral.software.PhoenixStore10.integrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.repositorios.RepoUsuario;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepoUsuario repoUsuario;

    @BeforeEach
    void setUp() {
        repoUsuario.deleteAll();
        Usuario usuario = new Usuario();
        usuario.setUsunombres("1234");
        usuario.setUsuapellidos("1234");
        usuario.setUsucorreo("1234@1234");
        usuario.setUsutelefono("1234");
        usuario.setUsudireccion("1234");
        usuario.setUsucedula(1234L);
        usuario.setUsuusername("1234");
        usuario.setUsucontrasena("$2a$10$8EhyGrKvEudwoKM.MHQ1i.4mVSqEz/9pnmds56C.5qMWUOBzvBrpW");
        usuario.setUsurol("cliente");

        repoUsuario.save(usuario);
    }

    @Test
    public void loginExitoso() throws Exception {
        mockMvc.perform(post("/login")
                        .param("usuusername", "1234")
                        .param("usucontrasena", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    public void loginFallido() throws Exception {
        mockMvc.perform(post("/login")
                        .param("usuusername", "admin")
                        .param("usucontrasena", "claveIncorrecta"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }
}
