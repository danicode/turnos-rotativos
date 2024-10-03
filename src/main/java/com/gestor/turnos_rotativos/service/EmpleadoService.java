package com.gestor.turnos_rotativos.service;

import com.gestor.turnos_rotativos.dto.EmpleadoDTO;
import com.gestor.turnos_rotativos.entity.Empleado;

import java.util.List;

public interface EmpleadoService {

    public Long create(EmpleadoDTO empleadoDTO);

    public List<EmpleadoDTO> get();

    public EmpleadoDTO getById(Long id);

    public Empleado getEntityById(Long id);

    public EmpleadoDTO update(Long id, EmpleadoDTO empleadoDTO);

    public void delete(Long id);

    public EmpleadoDTO getByNroDocumento(Integer nroDocumento);
}
