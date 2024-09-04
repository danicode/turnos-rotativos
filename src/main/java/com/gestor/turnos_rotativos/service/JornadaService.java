package com.gestor.turnos_rotativos.service;

import com.gestor.turnos_rotativos.dto.JornadaRequestDTO;
import com.gestor.turnos_rotativos.dto.JornadaResponseDTO;

import java.util.List;

public interface JornadaService {

    public List<JornadaResponseDTO> get();

    public JornadaResponseDTO create(JornadaRequestDTO jornadaRequestDTO);

    public JornadaResponseDTO getById(Long id);
}
