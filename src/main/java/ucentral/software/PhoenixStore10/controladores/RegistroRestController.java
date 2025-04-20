package ucentral.software.PhoenixStore10.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucentral.software.PhoenixStore10.entidades.Usuario;
import ucentral.software.PhoenixStore10.repositorios.RepoUsuario;

@RestController
@RequestMapping("/registro")
public class RegistroRestController {

    @Autowired
    private RepoUsuario repositorioUsuario;

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (repositorioUsuario.existsByUsuusername(usuario.getUsuusername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe.");
        }
        repositorioUsuario.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente.");
    }
}
