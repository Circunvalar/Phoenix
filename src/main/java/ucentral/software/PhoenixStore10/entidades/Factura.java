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

    @Column(nullable = false)
    private String cliente;

    @Column(nullable = false)
    private String numero; // Número de factura

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String direccion; // Dirección del cliente

    @Column(nullable = false)
    private String correo; // Correo del cliente

    @Column(nullable = false)
    private Double subtotal; // Subtotal de la factura

    @Column(nullable = false)
    private Double iva; // IVA

    @Column(nullable = false)
    private Double total; // Total a pagar

    @Column(nullable = false)
    private String formaPago; // Forma de pago

    @Column(nullable = false)
    private String condicionVenta; // Condición de venta

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles;
}