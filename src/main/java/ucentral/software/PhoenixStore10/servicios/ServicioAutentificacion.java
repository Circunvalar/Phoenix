package ucentral.software.PhoenixStore10.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucentral.software.PhoenixStore10.configs.PasswordEncrypt;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.repositorios.RepoUsuario;

import java.util.Optional;

@Service
public class ServicioAutentificacion {
    @Autowired
    RepoUsuario repoUsuario;
    @Autowired
    PasswordEncrypt passwordEncrypt;

    public boolean inicioSesion(String usu_username, String usu_contrasena) {
        Optional<Usuario> clienteOptional = repoUsuario.findByUsuusername(usu_username);
        if (clienteOptional.isPresent()) {
            Usuario cliente = clienteOptional.get();
            return passwordEncrypt.checkPassword(usu_contrasena, cliente.getUsucontrasena());
        }
        return false;
    }

    public String definirRol(String usu_username) {
        Optional<Usuario> clienteOptional = repoUsuario.findByUsuusername(usu_username);
        Usuario cliente = clienteOptional.get();
        return cliente.getUsurol();
    }
}
