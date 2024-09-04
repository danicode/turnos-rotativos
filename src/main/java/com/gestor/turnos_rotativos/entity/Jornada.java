package com.gestor.turnos_rotativos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="jornadas")
public class Jornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado")
    @NotNull(message = "'idEmpleado' es obligatorio.")
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_concepto")
    @NotNull(message = "'idConcepto' es obligatorio.")
    private ConceptoLaboral conceptoLaboral;

    @NotNull(message = "'fecha' es obligatoria.")
    @Column(name = "fecha", nullable = false)
    @PastOrPresent(message = "'fecha' no puede ser futura.")
    private LocalDate fecha;

    @Column(name = "horas_trabajadas")
    private Integer horasTrabajadas;
}
