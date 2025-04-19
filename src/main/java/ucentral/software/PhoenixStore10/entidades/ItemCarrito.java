package ucentral.software.PhoenixStore10.entidades;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ucentral.software.PhoenixStore10.entidades.Productos;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrito {
    private Productos producto;
    private int cantidad;

}
