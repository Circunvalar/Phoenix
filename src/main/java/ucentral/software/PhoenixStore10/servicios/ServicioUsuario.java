package ucentral.software.PhoenixStore10.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ucentral.software.PhoenixStore10.configs.PasswordEncrypt;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.repositorios.RepoUsuario;

@Service
public class ServicioUsuario {

    private final PasswordEncrypt passwordEncrypt;

    private final RepoUsuario repoUsuario;

    @Autowired
    public ServicioUsuario(RepoUsuario repoUsuario, PasswordEncrypt passwordEncrypt) {
        this.repoUsuario = repoUsuario;
        this.passwordEncrypt = passwordEncrypt;
    }

    public Usuario obtenerPorUsername(String username) {
        return repoUsuario.findByUsuusername(username).orElse(null);
    }

    public String registrarUsuario(Usuario usuario, Model model) {
        if (repoUsuario.existsByUsucorreo(usuario.getUsucorreo())) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "register";
        }
        if (repoUsuario.existsByUsucedula(usuario.getUsucedula())) {
            model.addAttribute("error", "La cédula ya está registrada.");
            return "register";
        }
        if (repoUsuario.existsByUsuusername(usuario.getUsuusername())) {
            model.addAttribute("error", "El nombre de usuario ya está registrado.");
            return "register";
        }
        // Encriptar la contraseña antes de guardar
        String hashedPassword = passwordEncrypt.hashPassword(usuario.getUsucontrasena());
        usuario.setUsucontrasena(hashedPassword);

        usuario.setUsurol("cliente");
        repoUsuario.save(usuario);
        model.addAttribute("success", "Usuario registrado exitosamente.");
        return "home";
    }

}
