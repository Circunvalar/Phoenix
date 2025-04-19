package ucentral.software.PhoenixStore10.servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ucentral.software.PhoenixStore10.configs.PasswordEncrypt;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.repositorios.RepoUsuario;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioAutentificacionTest {

    @Mock
    private RepoUsuario repoUsuario;

    @Mock
    private PasswordEncrypt passwordEncrypt;

    @InjectMocks
    private ServicioAutentificacion servicioAutentificacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void inicioSesion_UsuarioCorrectoContrasenaCorrecta() {
        Usuario usuario = new Usuario();
        usuario.setUsuusername("admin");
        usuario.setUsucontrasena("hashedPassword");

        when(repoUsuario.findByUsuusername("admin")).thenReturn(Optional.of(usuario));
        when(passwordEncrypt.checkPassword("1234", "hashedPassword")).thenReturn(true);

        assertTrue(servicioAutentificacion.inicioSesion("admin", "1234"));
    }

    @Test
    void inicioSesion_UsuarioCorrectoContrasenaIncorrecta() {
        Usuario usuario = new Usuario();
        usuario.setUsuusername("admin");
        usuario.setUsucontrasena("hashedPassword");

        when(repoUsuario.findByUsuusername("admin")).thenReturn(Optional.of(usuario));
        when(passwordEncrypt.checkPassword("wrong", "hashedPassword")).thenReturn(false);

        assertFalse(servicioAutentificacion.inicioSesion("admin", "wrong"));
    }

    @Test
    void inicioSesion_UsuarioNoExiste() {
        when(repoUsuario.findByUsuusername("desconocido")).thenReturn(Optional.empty());

        assertFalse(servicioAutentificacion.inicioSesion("desconocido", "1234"));
    }

    @Test
    void definirRol_UsuarioExiste() {
        Usuario usuario = new Usuario();
        usuario.setUsuusername("admin");
        usuario.setUsurol("ADMIN");

        when(repoUsuario.findByUsuusername("admin")).thenReturn(Optional.of(usuario));

        assertEquals("ADMIN", servicioAutentificacion.definirRol("admin"));
    }

    @Test
    void definirRol_UsuarioNoExiste_LanzaExcepcion() {
        when(repoUsuario.findByUsuusername("desconocido")).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () -> {
            servicioAutentificacion.definirRol("desconocido");
        });
    }
}
