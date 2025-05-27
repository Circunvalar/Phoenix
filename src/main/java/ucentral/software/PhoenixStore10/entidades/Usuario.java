package ucentral.software.PhoenixStore10.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuari")
@Table(name = "USUARIO")
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuid;

    @Column
    @NotBlank(message = "El nombre es obligatorio")
    private String usunombres;

    @Column
    @NotBlank(message = "El username es obligatorio")
    private String usuusername;

    @Column
    @NotBlank(message = "Los apellidos son obligatorios")
    private String usuapellidos;

    @Column(unique = true, nullable = false)
    @NotNull(message = "La cédula es obligatoria")
    private Long usucedula;

    @Column
    @NotBlank(message = "La contraseña es obligatoria")
    private String usucontrasena;

    @Column
    @NotBlank(message = "El rol es obligatorio")
    private String usurol;

    @Column
    @Email(message = "Debe ser un correo válido")
    @NotBlank(message = "El correo es obligatorio")
    private String usucorreo;

    @Column
    @NotBlank(message = "El teléfono es obligatorio")
    private String usutelefono;

    @Column
    private String usudireccion;

    @NotNull (message = "Obligatorio")
    private boolean usuestado; // Activo o Inactivo
}

