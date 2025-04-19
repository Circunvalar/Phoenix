package ucentral.software.PhoenixStore10.entidades;
import lombok.Data;

@Data
public class ItemCarrito {

    private Productos producto;
    private int cantidad;

    public ItemCarrito(Productos producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public void incrementarCantidad() {
        this.cantidad++;
    }

    public double getSubtotal() {
        return producto.getProprecio() * cantidad;
    }
}
