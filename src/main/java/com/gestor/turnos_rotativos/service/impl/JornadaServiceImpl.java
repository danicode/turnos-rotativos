package com.gestor.turnos_rotativos.service.impl;

import com.gestor.turnos_rotativos.dto.JornadaRequestDTO;
import com.gestor.turnos_rotativos.dto.JornadaResponseDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import com.gestor.turnos_rotativos.entity.Empleado;
import com.gestor.turnos_rotativos.entity.Jornada;
import com.gestor.turnos_rotativos.exception.BusinessException;
import com.gestor.turnos_rotativos.mapper.GenericExtendedMapper;
import com.gestor.turnos_rotativos.mapper.impl.JornadaMapperImpl;
import com.gestor.turnos_rotativos.repository.JornadaRepository;
import com.gestor.turnos_rotativos.service.ConceptoLaboralService;
import com.gestor.turnos_rotativos.service.EmpleadoService;
import com.gestor.turnos_rotativos.service.JornadaService;
import com.gestor.turnos_rotativos.validator.JornadaValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class JornadaServiceImpl implements JornadaService {

    private final JornadaRepository repository;
    private final GenericExtendedMapper<Jornada, JornadaResponseDTO, JornadaRequestDTO, Empleado, ConceptoLaboral> mapper = JornadaMapperImpl.getInstance();
    private final EmpleadoService empleadoService;
    private final ConceptoLaboralService conceptoLaboralService;
    private final JornadaValidator jornadaValidator;

    @Override
    public List<JornadaResponseDTO> get(Integer nroDocumento, LocalDate fechaDesde, LocalDate fechaHasta) {
        if (nroDocumento != null && fechaDesde != null && fechaHasta != null) {
            return mapper.toDtos(repository.findByEmpleadoNroDocumentoAndFechaBetween(nroDocumento, fechaDesde, fechaHasta));
        }

        if (nroDocumento != null) {
            return mapper.toDtos(repository.findByEmpleadoNroDocumento(nroDocumento));
        }

        if (fechaDesde != null && fechaHasta != null) {
            return mapper.toDtos(repository.findByFechaBetween(fechaDesde, fechaHasta));
        }

        if (fechaDesde != null) {
            return mapper.toDtos(repository.findByFechaAfterOrEqual(fechaDesde));
        }

        if (fechaHasta != null) {
            return mapper.toDtos(repository.findByFechaBeforeOrEqual(fechaHasta));
        }

        return mapper.toDtos(repository.findAll());
    }

    @Override
    public JornadaResponseDTO create(JornadaRequestDTO jornadaRequestDTO) {
        Empleado empleado = empleadoService.getEntityById(jornadaRequestDTO.getIdEmpleado());
        ConceptoLaboral conceptoLaboral = conceptoLaboralService.getEntityById(jornadaRequestDTO.getIdConcepto());

        jornadaValidator.validateJornadaRequest(jornadaRequestDTO, empleado, conceptoLaboral);

        Jornada jornada = mapper.toEntity(jornadaRequestDTO, empleado, conceptoLaboral);
        jornada = repository.save(jornada);
        return mapper.toDto(jornada);
    }

    @Override
    public JornadaResponseDTO getById(Long id) {
        return mapper.toDto(existById(id));
    }

    private Jornada existById(Long id) {
        Optional<Jornada> jornada = this.repository.findById(id);
        if (jornada.isPresent()) {
            return jornada.get();
        } else {
            throw new BusinessException("No se encontró la jornada con Id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
