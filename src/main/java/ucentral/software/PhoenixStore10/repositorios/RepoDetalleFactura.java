package ucentral.software.PhoenixStore10.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.software.PhoenixStore10.entidades.DetalleFactura;

public interface RepoDetalleFactura extends JpaRepository<DetalleFactura, Long> {
}
