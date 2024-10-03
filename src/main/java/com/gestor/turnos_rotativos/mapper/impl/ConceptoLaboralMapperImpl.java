package com.gestor.turnos_rotativos.mapper.impl;

import com.gestor.turnos_rotativos.dto.ConceptoLaboralDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import com.gestor.turnos_rotativos.mapper.DataMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class ConceptoLaboralMapperImpl implements DataMapper<ConceptoLaboral, ConceptoLaboralDTO> {

    private static final ConceptoLaboralMapperImpl INSTANCE = new ConceptoLaboralMapperImpl();

    public static ConceptoLaboralMapperImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public ConceptoLaboralDTO toDto(ConceptoLaboral conceptoLaboral) {
        if (conceptoLaboral == null) {
            return null;
        }

        ConceptoLaboralDTO conceptoLaboralDTO = new ConceptoLaboralDTO();
        conceptoLaboralDTO.setLaborable(conceptoLaboral.getLaborable());
        conceptoLaboralDTO.setNombre(conceptoLaboral.getNombre());
        conceptoLaboralDTO.setHsMinimo(conceptoLaboral.getHsMinimo());
        conceptoLaboralDTO.setHsMaximo(conceptoLaboral.getHsMaximo());

        return conceptoLaboralDTO;
    }

    @Override
    public List<ConceptoLaboralDTO> toDtos(List<ConceptoLaboral> conceptoLaborales) {
        if (conceptoLaborales == null) {
            return null;
        }

        return conceptoLaborales.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
