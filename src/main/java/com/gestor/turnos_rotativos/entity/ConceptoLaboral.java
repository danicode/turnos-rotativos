package com.gestor.turnos_rotativos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "concepto_laboral")
public class ConceptoLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Column(name = "hs_minimo")
    private Integer hsMinimo;

    @Column(name = "hs_maximo")
    private Integer hsMaximo;

    private Boolean laborable;
}
