package ucentral.software.PhoenixStore10.repositorios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucentral.software.PhoenixStore10.entidades.Usuario;

import java.util.Optional;

@Repository
public interface RepoUsuario extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByUsuusername(String usuusername);

    Optional<Usuario> findByUsucedula(Long usucedula);

    boolean existsByUsuusername(String usuusername);

    boolean existsByUsucedula(@NotNull(message = "La cédula es obligatoria") Long usucedula);

    boolean existsByUsucorreo(@Email(message = "Debe ser un correo válido") @NotBlank(message = "El correo es obligatorio") String usucorreo);
}
