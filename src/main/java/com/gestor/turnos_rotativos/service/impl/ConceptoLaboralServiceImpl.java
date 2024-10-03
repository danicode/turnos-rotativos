package com.gestor.turnos_rotativos.service.impl;

import com.gestor.turnos_rotativos.dto.ConceptoLaboralDTO;
import com.gestor.turnos_rotativos.dto.EmpleadoDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import com.gestor.turnos_rotativos.exception.BusinessException;
import com.gestor.turnos_rotativos.exception.BusinessRuleFieldException;
import com.gestor.turnos_rotativos.mapper.DataMapper;
import com.gestor.turnos_rotativos.mapper.impl.ConceptoLaboralMapperImpl;
import com.gestor.turnos_rotativos.repository.ConceptoLaboralRepository;
import com.gestor.turnos_rotativos.service.ConceptoLaboralService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConceptoLaboralServiceImpl implements ConceptoLaboralService {

    private final ConceptoLaboralRepository repository;
    private final DataMapper<ConceptoLaboral, ConceptoLaboralDTO> mapper = ConceptoLaboralMapperImpl.getInstance();

    @Override
    public List<ConceptoLaboralDTO> get(Integer id, String nombre) {
        List<ConceptoLaboralDTO> conceptos;

        if (id == null && nombre == null) {
            // Obtener todos los conceptos
            conceptos = mapper.toDtos(repository.findAll());
        } else if (id != null && nombre != null) {
            // Buscar por id y nombre
            conceptos = mapper.toDtos(repository.findByIdAndNombreContainingIgnoreCase(id, nombre));
        } else if (id != null) {
            // Buscar solo por id
            conceptos = mapper.toDtos(repository.findById(id).stream().toList());
        } else {
            // Buscar solo por nombre
            conceptos = mapper.toDtos(repository.findByNombreContainingIgnoreCase(nombre));
        }

        return conceptos;
    }

    @Override
    public ConceptoLaboralDTO getByNombre(String nombre) {
        return mapper.toDto(repository.findByNombre(nombre));
    }

    @Override
    public ConceptoLaboral getEntityById(Integer id) {
        return existEntityById(id);
    }

    private ConceptoLaboral existById(Integer id) {
        return findOrFail(id, "No se encontró el concepto laboral con Id: " + id);
    }

    private ConceptoLaboral existEntityById(Integer id) {
        return findOrFail(id, "No existe el concepto ingresado.");
    }

    private ConceptoLaboral findOrFail(Integer id, String errorMessage) {
        Optional<ConceptoLaboral> conceptoLaboral = repository.findById(id);
        return conceptoLaboral.orElseThrow(() -> new BusinessException(errorMessage, HttpStatus.NOT_FOUND));
    }

    private void validationDates(EmpleadoDTO empleadoDTO) {
        if (Period.between(empleadoDTO.getFechaNacimiento(), LocalDate.now()).getYears() < 18) {
            throw new BusinessRuleFieldException("La edad del empleado no puede ser menor a 18 años.", "fechaNacimiento");
        }
    }
}
