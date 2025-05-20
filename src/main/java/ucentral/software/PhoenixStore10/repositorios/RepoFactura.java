package ucentral.software.PhoenixStore10.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import ucentral.software.PhoenixStore10.entidades.Factura;

public interface RepoFactura extends JpaRepository<Factura, Long> {
}
