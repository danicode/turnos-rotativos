package com.gestor.turnos_rotativos.validator;

import com.gestor.turnos_rotativos.dto.JornadaRequestDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import com.gestor.turnos_rotativos.entity.Empleado;
import com.gestor.turnos_rotativos.entity.Jornada;
import com.gestor.turnos_rotativos.exception.BusinessException;
import com.gestor.turnos_rotativos.exception.BusinessRuleFieldException;
import com.gestor.turnos_rotativos.repository.JornadaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@AllArgsConstructor
@Component
public class JornadaValidator {

    private JornadaRepository repository;

    public void validateJornadaRequestParam(String nroDocumento, String fechaDesde, String fechaHasta) {
        validateDateFormat(fechaDesde);
        validateDateFormat(fechaHasta);
        validateNroDocumento(nroDocumento);
        validateDateRange(fechaDesde, fechaHasta);
    }

    private void validateDateFormat(String date) {
        if (date != null && !isValidDateFormat(date)) {
            throw new BusinessException("Los campos ‘fechaDesde’ y ‘fechaHasta’ deben respetar el formato yyyy-mm-dd.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateNroDocumento(String nroDocumento) {
        if (nroDocumento != null && !isValidNroDocumento(nroDocumento)) {
            throw new BusinessException("El campo ‘nroDocumento’ solo puede contener números enteros.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateDateRange(String fechaDesde, String fechaHasta) {
        if (fechaDesde != null && fechaHasta != null && fechaDesde.compareTo(fechaHasta) > 0) {
            throw new BusinessException("El campo ‘fechaDesde’ no puede ser mayor que ‘fechaHasta’.", HttpStatus.BAD_REQUEST);
        }
    }

    // Validaciones adicionales
    private boolean isValidDateFormat(String date) {
        // Aquí implementas la validación para verificar el formato de la fecha yyyy-mm-dd.
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    private boolean isValidNroDocumento(String nroDocumento) {
        // Verifica si el nroDocumento es un número entero
        return nroDocumento.matches("\\d+");
    }

    public void validateJornadaRequest(JornadaRequestDTO jornadaRequestDTO, Empleado empleado, ConceptoLaboral conceptoLaboral) {

        LocalDate inicioSemana = jornadaRequestDTO.getFecha().with(DayOfWeek.MONDAY);
        LocalDate finSemana = jornadaRequestDTO.getFecha().with(DayOfWeek.FRIDAY);

        List<Jornada> jornadasEmpleado = repository.findByEmpleadoId(empleado.getId());

        validarCamposObligatorios(jornadaRequestDTO, conceptoLaboral);
        if (jornadaRequestDTO.getIdConcepto() != 3) {
            validarHorasTrabajadas(jornadaRequestDTO, conceptoLaboral);
            validarHorasDiarias(jornadaRequestDTO, jornadasEmpleado);
            validarHorasSemanales(jornadaRequestDTO, inicioSemana, finSemana);
            validarHorasMensuales(jornadaRequestDTO, jornadasEmpleado);
        }
        validarDiaLibre(jornadaRequestDTO, jornadasEmpleado);
        validarTurnosExtraSemanales(jornadaRequestDTO, jornadasEmpleado);
        validarTurnosNormalesSemanales(jornadaRequestDTO, jornadasEmpleado, inicioSemana, finSemana);
        validarDiasLibres(jornadaRequestDTO, jornadasEmpleado);
        validarMaximoEmpleadosPorConcepto(jornadaRequestDTO);
        validarDuplicidadConceptoPorFecha(jornadaRequestDTO, jornadasEmpleado);
    }

    private void validarCamposObligatorios(JornadaRequestDTO jornadaRequestDTO, ConceptoLaboral conceptoLaboral) {
        if (conceptoLaboral.getNombre() != null) {
            if ((conceptoLaboral.getNombre().equals("Turno Normal") || conceptoLaboral.getNombre().equals("Turno Extra"))
                    && jornadaRequestDTO.getHorasTrabajadas() == null) {
                throw new BusinessRuleFieldException("'horasTrabajadas' es obligatorio para el concepto ingresado.", "horasTrabajadas");
            }

            if (conceptoLaboral.getNombre().equals("Día Libre") && jornadaRequestDTO.getHorasTrabajadas() != null) {
                throw new BusinessRuleFieldException("El concepto ingresado no requiere el ingreso de 'horasTrabajadas'.", "horasTrabajadas");
            }
        }
    }

    private void validarHorasTrabajadas(JornadaRequestDTO jornadaRequestDTO, ConceptoLaboral conceptoLaboral) {
        if (jornadaRequestDTO.getHorasTrabajadas() < conceptoLaboral.getHsMinimo() ||
                jornadaRequestDTO.getHorasTrabajadas() > conceptoLaboral.getHsMaximo()) {
            throw new BusinessException("El rango de horas que se puede cargar para este concepto es de " +
                    conceptoLaboral.getHsMinimo() + " - " + conceptoLaboral.getHsMaximo());
        }
    }

    private void validarHorasDiarias(JornadaRequestDTO jornadaRequestDTO, List<Jornada> jornadas) {
        int horasTotalesDia = jornadaRequestDTO.getHorasTrabajadas();

        for (Jornada jornada : jornadas) {
            if (jornada.getFecha().equals(jornadaRequestDTO.getFecha())) {
                horasTotalesDia += jornada.getHorasTrabajadas();
            }
        }

        if (horasTotalesDia > 14) {
            throw new BusinessException("Un empleado no puede cargar más de 14 horas trabajadas en un día.");
        }
    }

    public void validarHorasSemanales(JornadaRequestDTO jornadaRequestDTO, LocalDate fechaInicioSemana, LocalDate fechaFinSemana) {

        // Obtener todas las jornadas del empleado en la semana actual
        List<Jornada> jornadasSemanaActual = repository.findByEmpleadoIdAndFechaBetween(jornadaRequestDTO.getIdEmpleado(), fechaInicioSemana, fechaFinSemana);

        // Calcular las horas totales trabajadas en la semana
        int horasTotalesSemanales = jornadasSemanaActual.stream()
                .mapToInt(Jornada::getHorasTrabajadas)
                .sum();

        // Verificar si con las nuevas horas se supera el límite de 52 horas
        if (horasTotalesSemanales + jornadaRequestDTO.getHorasTrabajadas() > 52) {
            throw new BusinessException("El empleado ingresado supera las 52 horas semanales.");
        }
    }

    private void validarHorasMensuales(JornadaRequestDTO jornadaRequestDTO, List<Jornada> jornadas) {
        YearMonth mes = YearMonth.from(jornadaRequestDTO.getFecha());
        int horasTotalesMes = jornadaRequestDTO.getHorasTrabajadas();

        for (Jornada jornada : jornadas) {
            if (YearMonth.from(jornada.getFecha()).equals(mes)) {
                horasTotalesMes += jornada.getHorasTrabajadas();
            }
        }

        if (horasTotalesMes > 190) {
            throw new BusinessException("El empleado ingresado supera las 190 horas mensuales.");
        }
    }

    private void validarDiaLibre(JornadaRequestDTO jornadaRequestDTO, List<Jornada> jornadas) {
        for (Jornada jornada : jornadas) {
            if (jornada.getFecha().equals(jornadaRequestDTO.getFecha()) && jornada.getConceptoLaboral().getId() == 3) {
                throw new BusinessException("El empleado ingresado cuenta con un día libre en esa fecha.");
            }
        }
    }

    private void validarTurnosExtraSemanales(JornadaRequestDTO jornadaRequestDTO, List<Jornada> jornadas) {
        LocalDate fechaInicioSemana = jornadaRequestDTO.getFecha().with(DayOfWeek.MONDAY);
        LocalDate fechaFinSemana = jornadaRequestDTO.getFecha().with(DayOfWeek.FRIDAY);
        long contadorTurnosExtra = jornadas.stream()
                .filter(j -> !j.getFecha().isBefore(fechaInicioSemana) && !j.getFecha().isAfter(fechaFinSemana))
                .filter(j -> j.getConceptoLaboral().getId() == 2)
                .count();

        if (contadorTurnosExtra >= 3) {
            throw new BusinessException("El empleado ingresado ya cuenta con 3 turnos extra esta semana.");
        }
    }

    private void validarTurnosNormalesSemanales(JornadaRequestDTO jornadaRequestDTO, List<Jornada> jornadas, LocalDate fechaInicioSemana, LocalDate fechaFinSemana) {
        long contadorTurnosNormales = jornadas.stream()
                .filter(j -> !j.getFecha().isBefore(fechaInicioSemana) && !j.getFecha().isAfter(fechaFinSemana))
                .filter(j -> j.getConceptoLaboral().getId() == 1)
                .count();

        if (contadorTurnosNormales >= 5) {
            throw new BusinessException("El empleado ingresado ya cuenta con 5 turnos normales esta semana.");
        }
    }

    private void validarDiasLibres(JornadaRequestDTO jornadaRequestDTO, List<Jornada> jornadas) {
        LocalDate fechaInicioSemana = jornadaRequestDTO.getFecha().with(DayOfWeek.MONDAY);
        LocalDate fechaFinSemana = jornadaRequestDTO.getFecha().with(DayOfWeek.FRIDAY);
        YearMonth mes = YearMonth.from(jornadaRequestDTO.getFecha());

        long diasLibresSemana = jornadas.stream()
                .filter(j -> !j.getFecha().isBefore(fechaInicioSemana) && !j.getFecha().isAfter(fechaFinSemana))
                .filter(j -> j.getConceptoLaboral().getId() == 3)
                .count();

        long diasLibresMes = jornadas.stream()
                .filter(j -> YearMonth.from(j.getFecha()).equals(mes))
                .filter(j -> j.getConceptoLaboral().getId() == 3)
                .count();

        if (diasLibresSemana >= 2) {
            throw new BusinessException("El empleado no cuenta con más días libres esta semana.");
        }

        if (diasLibresMes >= 5) {
            throw new BusinessException("El empleado no cuenta con más días libres este mes.");
        }
    }

    private void validarMaximoEmpleadosPorConcepto(JornadaRequestDTO jornadaRequestDTO) {
        Integer conceptoId = jornadaRequestDTO.getIdConcepto();
        LocalDate fecha = jornadaRequestDTO.getFecha();

        // Realiza la consulta a la base de datos para contar cuántos empleados están registrados
        int count = repository.countByConceptoIdAndFecha(conceptoId, fecha);

        if (count >= 2) {
            throw new BusinessException("Ya existen 2 empleados registrados para este concepto en la fecha ingresada.");
        }
    }

    private void validarDuplicidadConceptoPorFecha(JornadaRequestDTO jornadaRequestDTO, List<Jornada> jornadas) {
        boolean existeJornadaConMismoConcepto = jornadas.stream()
                .anyMatch(j -> j.getFecha().equals(jornadaRequestDTO.getFecha()) && j.getConceptoLaboral().getId().equals(jornadaRequestDTO.getIdConcepto()));

        if (existeJornadaConMismoConcepto) {
            throw new BusinessException("El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.");
        }
    }
}
