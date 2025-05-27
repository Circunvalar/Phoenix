package ucentral.software.PhoenixStore10.entidades;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", referencedColumnName = "usuid", nullable = false)
    private Usuario cliente; // Relación con el cliente

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double iva;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String formaPago;

    @Column(nullable = false)
    private String condicionVenta;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles;
}