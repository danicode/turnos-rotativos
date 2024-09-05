package com.gestor.turnos_rotativos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class JornadaResponseDTO {

    @JsonIgnore
    private Long id;
    private Integer nroDocumento;
    private String nombreCompleto;
    private LocalDate fecha;
    private String concepto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer horasTrabajadas;
}
