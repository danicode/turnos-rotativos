package com.gestor.turnos_rotativos.service;

import com.gestor.turnos_rotativos.dto.ConceptoLaboralDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import java.util.List;

public interface ConceptoLaboralService {

    public List<ConceptoLaboralDTO> get(Integer id, String nombre);
    public ConceptoLaboralDTO getByNombre(String nombre);
    public ConceptoLaboral getEntityById(Integer id);
}
