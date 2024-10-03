package com.gestor.turnos_rotativos.mapper.impl;

import com.gestor.turnos_rotativos.dto.EmpleadoDTO;
import com.gestor.turnos_rotativos.entity.Empleado;
import com.gestor.turnos_rotativos.mapper.GenericMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class EmpleadoMapperImpl implements GenericMapper<Empleado, EmpleadoDTO> {

    private static final EmpleadoMapperImpl INSTANCE = new EmpleadoMapperImpl();

    public static EmpleadoMapperImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public EmpleadoDTO toDto(Empleado empleado) {
        if (empleado == null) {
            return null;
        }

        EmpleadoDTO empleadoDTODto = new EmpleadoDTO();
        empleadoDTODto.setNroDocumento(empleado.getNroDocumento());
        empleadoDTODto.setNombre(empleado.getNombre());
        empleadoDTODto.setApellido(empleado.getApellido());
        empleadoDTODto.setEmail(empleado.getEmail());
        empleadoDTODto.setFechaNacimiento(empleado.getFechaNacimiento());
        empleadoDTODto.setFechaIngreso(empleado.getFechaIngreso());

        return empleadoDTODto;
    }

    @Override
    public List<EmpleadoDTO> toDtos(List<Empleado> empleados) {
        if (empleados == null) {
            return null;
        }

        return empleados.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Empleado toEntity(EmpleadoDTO empleadoDto) {
        if (empleadoDto == null) {
            return null;
        }

        Empleado empleado = new Empleado();
        empleado.setNroDocumento(empleadoDto.getNroDocumento());
        empleado.setNombre(empleadoDto.getNombre());
        empleado.setApellido(empleadoDto.getApellido());
        empleado.setEmail(empleadoDto.getEmail());
        empleado.setFechaNacimiento(empleadoDto.getFechaNacimiento());
        empleado.setFechaIngreso(empleadoDto.getFechaIngreso());

        return empleado;
    }

    @Override
    public void updateEntityFromDto(Empleado empleado, EmpleadoDTO empleadoDto) {
        if (empleadoDto == null || empleado == null) {
            return;
        }

        empleado.setNombre(empleadoDto.getNombre());
        empleado.setApellido(empleadoDto.getApellido());
        empleado.setEmail(empleadoDto.getEmail());
        empleado.setFechaIngreso(empleadoDto.getFechaIngreso());
        empleado.setFechaNacimiento(empleadoDto.getFechaNacimiento());
        empleado.setNroDocumento(empleadoDto.getNroDocumento());
    }
}
