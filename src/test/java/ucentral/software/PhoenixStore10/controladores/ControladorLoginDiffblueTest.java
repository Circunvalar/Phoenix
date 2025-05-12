package ucentral.software.PhoenixStore10.controladores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import ucentral.software.PhoenixStore10.configs.PasswordEncrypt;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.repositorios.RepoUsuario;
import ucentral.software.PhoenixStore10.servicios.ServicioAutentificacion;
import ucentral.software.PhoenixStore10.servicios.ServicioUsuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ControladorLoginDiffblueTest {
    /**
     * Test {@link ControladorLogin#mostrarFormularioLogin(Model)}.
     * <p>
     * Method under test: {@link ControladorLogin#mostrarFormularioLogin(Model)}
     */
    @Test
    @DisplayName("Test mostrarFormularioLogin(Model)")
    @Tag("MaintainedByDiffblue")
    void testMostrarFormularioLogin() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        RepoUsuario repoUsuario = mock(RepoUsuario.class);
        ServicioUsuario servicioUsuario = new ServicioUsuario(repoUsuario, new PasswordEncrypt());

        ControladorLogin controladorLogin = new ControladorLogin(servicioUsuario, new ServicioAutentificacion());
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualMostrarFormularioLoginResult = controladorLogin.mostrarFormularioLogin(model);

        // Assert
        assertEquals(1, model.size());
        Object getResult = model.get("usuario");
        assertTrue(getResult instanceof Usuario);
        assertEquals("login", actualMostrarFormularioLoginResult);
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
     * Test {@link ControladorLogin#iniciarSesion(Usuario, Model)}.
     * <ul>
     *   <li>Then {@link ConcurrentModel#ConcurrentModel()} {@code error} is {@code Rol no reconocido}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorLogin#iniciarSesion(Usuario, Model)}
     */
    @Test
    @DisplayName("Test iniciarSesion(Usuario, Model); then ConcurrentModel() 'error' is 'Rol no reconocido'")
    @Tag("MaintainedByDiffblue")
    void testIniciarSesion_thenConcurrentModelErrorIsRolNoReconocido() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        ServicioAutentificacion servicioAutentificacion = mock(ServicioAutentificacion.class);
        when(servicioAutentificacion.definirRol(Mockito.<String>any())).thenReturn("Definir Rol");
        when(servicioAutentificacion.inicioSesion(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        RepoUsuario repoUsuario = mock(RepoUsuario.class);
        ControladorLogin controladorLogin = new ControladorLogin(new ServicioUsuario(repoUsuario, new PasswordEncrypt()),
                servicioAutentificacion);

        Usuario usuario = new Usuario();
        usuario.setUsuapellidos("Usuapellidos");
        usuario.setUsucedula(1L);
        usuario.setUsucontrasena("Usucontrasena");
        usuario.setUsucorreo("Usucorreo");
        usuario.setUsudireccion("Usudireccion");
        usuario.setUsuid(1L);
        usuario.setUsunombres("Usunombres");
        usuario.setUsurol("Usurol");
        usuario.setUsutelefono("Usutelefono");
        usuario.setUsuusername("janedoe");
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualIniciarSesionResult = controladorLogin.iniciarSesion(usuario, model);

        // Assert
        verify(servicioAutentificacion).definirRol(eq("janedoe"));
        verify(servicioAutentificacion).inicioSesion(eq("janedoe"), eq("Usucontrasena"));
        assertEquals(2, model.size());
        assertEquals("Rol no reconocido", model.get("error"));
        assertEquals("login", actualIniciarSesionResult);
        assertSame(usuario, model.get("usuario"));
    }

    /**
     * Test {@link ControladorLogin#iniciarSesion(Usuario, Model)}.
     * <ul>
     *   <li>Then {@link ConcurrentModel#ConcurrentModel()} {@code error} is {@code Usuario o contraseña incorrectos}.</li>
     * </ul>
     * <p>
     * Method under test: {@link ControladorLogin#iniciarSesion(Usuario, Model)}
     */
    @Test
    @DisplayName("Test iniciarSesion(Usuario, Model); then ConcurrentModel() 'error' is 'Usuario o contraseña incorrectos'")
    @Tag("MaintainedByDiffblue")
    void testIniciarSesion_thenConcurrentModelErrorIsUsuarioOContraseAIncorrectos() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
        //   Run dcover create --keep-partial-tests to gain insights into why
        //   a non-Spring test was created.

        // Arrange
        ServicioAutentificacion servicioAutentificacion = mock(ServicioAutentificacion.class);
        when(servicioAutentificacion.inicioSesion(Mockito.<String>any(), Mockito.<String>any())).thenReturn(false);
        RepoUsuario repoUsuario = mock(RepoUsuario.class);
        ControladorLogin controladorLogin = new ControladorLogin(new ServicioUsuario(repoUsuario, new PasswordEncrypt()),
                servicioAutentificacion);

        Usuario usuario = new Usuario();
        usuario.setUsuapellidos("Usuapellidos");
        usuario.setUsucedula(1L);
        usuario.setUsucontrasena("Usucontrasena");
        usuario.setUsucorreo("Usucorreo");
        usuario.setUsudireccion("Usudireccion");
        usuario.setUsuid(1L);
        usuario.setUsunombres("Usunombres");
        usuario.setUsurol("Usurol");
        usuario.setUsutelefono("Usutelefono");
        usuario.setUsuusername("janedoe");
        ConcurrentModel model = new ConcurrentModel();

        // Act
        String actualIniciarSesionResult = controladorLogin.iniciarSesion(usuario, model);

        // Assert
        verify(servicioAutentificacion).inicioSesion(eq("janedoe"), eq("Usucontrasena"));
        assertEquals(2, model.size());
        assertEquals("Usuario o contraseña incorrectos", model.get("error"));
        assertEquals("login", actualIniciarSesionResult);
        assertSame(usuario, model.get("usuario"));
    }
}
