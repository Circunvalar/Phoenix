package ucentral.software.PhoenixStore10.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "producto")
@Table(name = "PRODUCTOS")
@Builder
public class Productos {

    @Id
    private Long procodigo;

    @Column(nullable = false)
    private String pronombre;

    @Column(nullable = false)
    private String prodescripcion;

    @Column(nullable = false)
    private Double proprecio;

    @Column(nullable = false)
    private String procategoria;

    @Column(nullable = false)
    private String proimagenUrl;
}
