package ucentral.software.PhoenixStore10.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucentral.software.PhoenixStore10.entidades.Usuario;

import java.util.Optional;

@Repository
public interface RepoUsuario extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByUsuusername(String usuusername);

    Optional<Usuario> findByUsucedula(Long usucedula);
}
