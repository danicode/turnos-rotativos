package com.gestor.turnos_rotativos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nro_documento", nullable = false, unique = true)
    @NotNull(message = "'nroDocumento' es obligatorio.")
    @Min(value = 1, message = "'nroDocumento' debe ser mayor a 0.")
    private Integer nroDocumento;

    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "'nombre' es obligatorio.")
    @Pattern(regexp = "[a-zA-Z]+$", message = "Solo se permiten letras en el campo 'nombre'.")
    private String nombre;

    @Column(name = "apellido", nullable = false)
    @NotBlank(message = "'apellido' es obligatorio.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Solo se permiten letras en el campo 'apellido'.")
    private String apellido;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "'email' es obligatorio.")
    @Email(message = "El email ingresado no es correcto.")
    private String email;

    @Column(name = "fecha_nacimiento", nullable = false)
    @NotNull(message = "Fecha de nacimiento es obligatorio.")
    @Past(message = "La fecha de nacimiento no puede ser posterior al día de la fecha.")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_ingreso", nullable = false)
    @NotNull(message = "Fecha de ingreso es obligatorio.")
    @PastOrPresent(message = "La fecha de ingreso no puede ser posterior al día de la fecha.")
    private LocalDate fechaIngreso;

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
