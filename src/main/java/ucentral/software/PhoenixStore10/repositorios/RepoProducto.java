package ucentral.software.PhoenixStore10.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucentral.software.PhoenixStore10.entidades.Productos;

import java.util.List;

@Repository
public interface RepoProducto extends JpaRepository<Productos, Long> {
    List<Productos> findByPronombreContainingIgnoreCase(String nombre);


    Productos findByPronombre(String nombre);
}


