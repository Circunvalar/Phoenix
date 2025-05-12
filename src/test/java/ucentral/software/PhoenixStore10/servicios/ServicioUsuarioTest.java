package ucentral.software.PhoenixStore10.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ucentral.software.PhoenixStore10.configs.PasswordEncrypt;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.repositorios.RepoUsuario;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioUsuarioTest {

    @Mock
    private RepoUsuario repoUsuario;

    @Mock
    private PasswordEncrypt passwordEncrypt;

    @InjectMocks
    private ServicioUsuario servicioUsuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerPorUsername_DeberiaRetornarUsuarioSiExiste() {
        Usuario usuario = new Usuario();
        usuario.setUsuusername("juan");

        when(repoUsuario.findByUsuusername("juan")).thenReturn(Optional.of(usuario));

        Usuario resultado = servicioUsuario.obtenerPorUsername("juan");
        assertNotNull(resultado);
        assertEquals("juan", resultado.getUsuusername());
    }

    @Test
    void obtenerPorUsername_DeberiaRetornarNullSiNoExiste() {
        when(repoUsuario.findByUsuusername("noExiste")).thenReturn(Optional.empty());

        Usuario resultado = servicioUsuario.obtenerPorUsername("noExiste");
        assertNull(resultado);
    }

/**
    @Test
    void registrarUsuario_DeberiaRegistrarCorrectamente() {
        Usuario usuario = new Usuario();
        usuario.setUsucedula(12345L);
        usuario.setUsuusername("nuevoUsuario");
        usuario.setUsucontrasena("password123");

        Model model = new ConcurrentModel();

        when(repoUsuario.findByUsucedula(12345L)).thenReturn(Optional.empty());
        when(repoUsuario.findByUsuusername("nuevoUsuario")).thenReturn(Optional.empty());
        when(passwordEncrypt.hashPassword("password123")).thenReturn("hashedPassword");

        String resultado = servicioUsuario.registrarUsuario(usuario, model);

        verify(repoUsuario).save(usuario);
        assertEquals("cliente", usuario.getUsurol());
        assertEquals("hashedPassword", usuario.getUsucontrasena());
        assertEquals("redirect:/login", resultado);
    }

    @Test
    void registrarUsuario_DeberiaMostrarErrorPorCedulaExistente() {
        Usuario usuario = new Usuario();
        usuario.setUsucedula(12345L);
        usuario.setUsuusername("nuevoUsuario");

        Model model = new ConcurrentModel();

        when(repoUsuario.findByUsucedula(12345L)).thenReturn(Optional.of(new Usuario()));

        String resultado = servicioUsuario.registrarUsuario(usuario, model);

        assertEquals("register", resultado);
        assertEquals("Ya existe un usuario con esta cédula.", model.getAttribute("error"));
        verify(repoUsuario, never()).save(any());
    }

    @Test
    void registrarUsuario_DeberiaMostrarErrorPorUsernameExistente() {
        Usuario usuario = new Usuario();
        usuario.setUsucedula(12345L);
        usuario.setUsuusername("usuarioExistente");

        Model model = new ConcurrentModel();

        when(repoUsuario.findByUsucedula(12345L)).thenReturn(Optional.empty());
        when(repoUsuario.findByUsuusername("usuarioExistente")).thenReturn(Optional.of(new Usuario()));

        String resultado = servicioUsuario.registrarUsuario(usuario, model);

        assertEquals("register", resultado);
        assertEquals("El nombre de usuario ya está en uso.", model.getAttribute("error"));
        verify(repoUsuario, never()).save(any());
    }
}
**/
}
