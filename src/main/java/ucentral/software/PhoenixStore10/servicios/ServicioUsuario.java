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
        if (repoUsuario.findByUsucedula(usuario.getUsucedula()).isPresent()) {
            model.addAttribute("error", "Ya existe un usuario con esta cédula.");
            return "register";
        }

        if (repoUsuario.findByUsuusername(usuario.getUsuusername()).isPresent()) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "register";
        }

        usuario.setUsurol("cliente");

        usuario.setUsucontrasena(passwordEncrypt.hashPassword(usuario.getUsucontrasena()));

        repoUsuario.save(usuario);

        return "redirect:/login";
    }
}
