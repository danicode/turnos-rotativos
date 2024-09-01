package com.gestor.turnos_rotativos.service;

import com.gestor.turnos_rotativos.dto.ConceptoLaboralDTO;

import java.util.List;

public interface ConceptoLaboralService {

    public List<ConceptoLaboralDTO> get(Integer id, String nombre);
}
