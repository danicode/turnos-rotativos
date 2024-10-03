package com.gestor.turnos_rotativos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ConceptoLaboralDTO {

    private String nombre;
    private Integer hsMinimo;
    private Integer hsMaximo;
    private Boolean laborable;
}
