package ucentral.software.PhoenixStore10.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.repositorios.RepoUsuario;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistroIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepoUsuario repoUsuario;

    @BeforeEach
    public void setup() {
        repoUsuario.deleteAll();

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setUsunombres("Pedro");
        usuarioExistente.setUsuapellidos("Ramírez");
        usuarioExistente.setUsucontrasena("password123");
        usuarioExistente.setUsucorreo("pedro@correo.com");
        usuarioExistente.setUsudireccion("Carrera 45");
        usuarioExistente.setUsurol("cliente");
        usuarioExistente.setUsutelefono("3111234567");
        usuarioExistente.setUsucedula(987654321L);
        usuarioExistente.setUsuusername("pedroram");

        repoUsuario.save(usuarioExistente);
    }

    @Test
    public void registroExitoso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsunombres("Juan");
        usuario.setUsuapellidos("Pérez");
        usuario.setUsucontrasena("contrasenaSegura");
        usuario.setUsucorreo("correo@correo.com");
        usuario.setUsudireccion("Direccion 123");
        usuario.setUsurol("cliente");
        usuario.setUsutelefono("3200000000");
        usuario.setUsucedula(491234123641234789L);
        usuario.setUsuusername("usuario" + UUID.randomUUID().toString().substring(0, 5));

        mockMvc.perform(post("/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk());
    }

    @Test
    public void registroNoExitosoPorCedulaDuplicada() throws Exception {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsunombres("Carlos");
        nuevoUsuario.setUsuapellidos("Lopez");
        nuevoUsuario.setUsucontrasena("nuevacontrasena");
        nuevoUsuario.setUsucorreo("nuevo@correo.com");
        nuevoUsuario.setUsudireccion("Avenida Siempre Viva");
        nuevoUsuario.setUsurol("CLIENTE");
        nuevoUsuario.setUsutelefono("0987654321");
        nuevoUsuario.setUsucedula(987654321L);
        nuevoUsuario.setUsuusername("nuevousuario");

        mockMvc.perform(post("/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Ya existe un usuario con ese correo o cédula."));
    }
}
