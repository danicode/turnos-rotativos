package com.gestor.turnos_rotativos.service;

import com.gestor.turnos_rotativos.dto.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {

    public Long create(EmpleadoDTO empleadoDTO);

    public List<EmpleadoDTO> get();

    public EmpleadoDTO getById(Long id);

    public EmpleadoDTO update(Long id, EmpleadoDTO empleadoDTO);

    public void delete(Long id);
}
