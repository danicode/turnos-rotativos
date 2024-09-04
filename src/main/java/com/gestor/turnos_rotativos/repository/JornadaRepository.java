package com.gestor.turnos_rotativos.repository;

import com.gestor.turnos_rotativos.entity.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long>  {

    List<Jornada> findByEmpleadoId(Long idEmpleado);
    List<Jornada> findByEmpleadoIdAndFechaBetween(Long idEmpleado, LocalDate inicioSemana, LocalDate finSemana);
    @Query("SELECT COUNT(j) FROM Jornada j WHERE j.conceptoLaboral.id = :conceptoId AND j.fecha = :fecha")
    Integer countByConceptoIdAndFecha(Integer conceptoId, LocalDate fecha);
}
