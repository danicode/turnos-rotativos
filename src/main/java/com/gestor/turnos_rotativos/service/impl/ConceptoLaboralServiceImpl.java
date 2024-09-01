package com.gestor.turnos_rotativos.service.impl;

import com.gestor.turnos_rotativos.dto.ConceptoLaboralDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import com.gestor.turnos_rotativos.mapper.DataMapper;
import com.gestor.turnos_rotativos.mapper.impl.ConceptoLaboralMapperImpl;
import com.gestor.turnos_rotativos.repository.ConceptoLaboralRepository;
import com.gestor.turnos_rotativos.service.ConceptoLaboralService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
