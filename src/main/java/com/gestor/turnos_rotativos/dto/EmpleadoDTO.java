package com.gestor.turnos_rotativos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class EmpleadoDTO {

    @NotNull(message = "'nroDocumento' es obligatorio.")
    private Integer nroDocumento;

    @NotBlank(message = "'nombre' es obligatorio.")
    @Pattern(regexp = "[A-Za-z]+$", message = "Solo se permiten letras en el campo 'nombre'.")
    private String nombre;

    @NotBlank(message = "'apellido' es obligatorio.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Solo se permiten letras en el campo 'apellido'.")
    private String apellido;

    @NotBlank(message = "'email' es obligatorio.")
    @Email(message = "El 'email' ingresado no es correcto.")
    private String email;

    @NotNull(message = "Fecha de nacimiento es obligatorio.")
    @Past(message = "La fecha de nacimiento no puede ser posterior al día de la fecha.")
    private LocalDate fechaNacimiento;

    @NotNull(message = "Fecha de ingreso es obligatorio.")
    @PastOrPresent(message = "La fecha de ingreso no puede ser posterior al día de la fecha.")
    private LocalDate fechaIngreso;
}
