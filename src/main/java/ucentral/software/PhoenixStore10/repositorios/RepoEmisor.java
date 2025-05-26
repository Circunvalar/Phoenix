package ucentral.software.PhoenixStore10.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.software.PhoenixStore10.entidades.Emisor;

public interface RepoEmisor extends JpaRepository<Emisor, Long> {
    Emisor findFirstByOrderByIdAsc();
}