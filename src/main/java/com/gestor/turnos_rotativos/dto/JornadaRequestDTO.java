package com.gestor.turnos_rotativos.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class JornadaRequestDTO {

    @NotNull(message = "'idEmpleado' es obligatorio.")
    private Long idEmpleado;

    @NotNull(message = "'idConcepto' es obligatorio.")
    private Integer idConcepto;

    @NotNull(message = "'fecha' es obligatoria.")
    @PastOrPresent(message = "'fecha' no puede ser futura.")
    private LocalDate fecha;

    private Integer horasTrabajadas;
}
