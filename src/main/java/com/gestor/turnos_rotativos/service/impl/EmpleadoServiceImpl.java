package com.gestor.turnos_rotativos.service.impl;

import com.gestor.turnos_rotativos.dto.EmpleadoDTO;
import com.gestor.turnos_rotativos.entity.Empleado;
import com.gestor.turnos_rotativos.exception.BusinessException;
import com.gestor.turnos_rotativos.exception.BusinessRuleFieldException;
import com.gestor.turnos_rotativos.mapper.GenericMapper;
import com.gestor.turnos_rotativos.mapper.impl.EmpleadoMapperImpl;
import com.gestor.turnos_rotativos.repository.EmpleadoRepository;
import com.gestor.turnos_rotativos.service.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository repository;
    private final GenericMapper<Empleado, EmpleadoDTO> mapper = EmpleadoMapperImpl.getInstance();

    @Override
    public Long create(EmpleadoDTO empleadoDTO) {
        validationDates(empleadoDTO);
        Empleado empleado = mapper.toEntity(empleadoDTO);
        empleado = repository.save(empleado);
        return empleado.getId();
    }

    @Override
    public List<EmpleadoDTO> get() {
        return mapper.toDtos(repository.findAll());
    }

    @Override
    public EmpleadoDTO getById(Long id) {
        return mapper.toDto(existById(id));
    }

    @Override
    public Empleado getEntityById(Long id) {
        return existEntityById(id);
    }

    @Override
    public EmpleadoDTO update(Long id, EmpleadoDTO empleadoDTO) {
        Empleado empleado = existById(id);
        validationDates(empleadoDTO);
        mapper.updateEntityFromDto(empleado, empleadoDTO);
        return mapper.toDto(repository.save(empleado));
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public EmpleadoDTO getByNroDocumento(Integer nroDocumento) {
        return mapper.toDto(repository.findByNroDocumento(nroDocumento));
    }

    private Empleado existById(Long id) {
        return findOrFail(id, "No se encontró el empleado con Id: " + id);
    }

    private Empleado existEntityById(Long id) {
        return findOrFail(id, "No existe el empleado ingresado.");
    }

    private Empleado findOrFail(Long id, String errorMessage) {
        Optional<Empleado> empleado = repository.findById(id);
        return empleado.orElseThrow(() -> new BusinessException(errorMessage, HttpStatus.NOT_FOUND));
    }

    private void validationDates(EmpleadoDTO empleadoDTO) {
        if (Period.between(empleadoDTO.getFechaNacimiento(), LocalDate.now()).getYears() < 18) {
            throw new BusinessRuleFieldException("La edad del empleado no puede ser menor a 18 años.", "fechaNacimiento");
        }
    }
}
