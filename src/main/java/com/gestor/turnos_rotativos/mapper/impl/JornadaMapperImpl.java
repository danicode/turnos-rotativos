package com.gestor.turnos_rotativos.mapper.impl;

import com.gestor.turnos_rotativos.dto.JornadaRequestDTO;
import com.gestor.turnos_rotativos.dto.JornadaResponseDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import com.gestor.turnos_rotativos.entity.Empleado;
import com.gestor.turnos_rotativos.entity.Jornada;
import com.gestor.turnos_rotativos.mapper.GenericExtendedMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class JornadaMapperImpl implements GenericExtendedMapper<Jornada, JornadaResponseDTO, JornadaRequestDTO, Empleado, ConceptoLaboral> {

    private static final JornadaMapperImpl INSTANCE = new JornadaMapperImpl();

    public static JornadaMapperImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public JornadaResponseDTO toDto(Jornada jornada) {

        if (jornada == null) {
            return null;
        }

        JornadaResponseDTO jornadaResponseDTO = new JornadaResponseDTO();
        jornadaResponseDTO.setId(jornada.getId());
        jornadaResponseDTO.setNombreCompleto(jornada.getEmpleado().getNombreCompleto());
        jornadaResponseDTO.setConcepto(jornada.getConceptoLaboral().getNombre());
        jornadaResponseDTO.setNroDocumento(jornada.getEmpleado().getNroDocumento());
        jornadaResponseDTO.setFecha(jornada.getFecha());
        jornadaResponseDTO.setHorasTrabajadas(jornada.getHorasTrabajadas());

        return jornadaResponseDTO;
    }

    @Override
    public List<JornadaResponseDTO> toDtos(List<Jornada> jornadas) {
        if (jornadas == null) {
            return null;
        }

        return jornadas.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Jornada toEntity(JornadaRequestDTO jornadaRequestDTO, Empleado empleado, ConceptoLaboral conceptoLaboral) {
        if (jornadaRequestDTO == null) {
            return null;
        }

        Jornada jornada = new Jornada();
        jornada.setFecha(jornadaRequestDTO.getFecha());
        jornada.setHorasTrabajadas(jornadaRequestDTO.getHorasTrabajadas());
        jornada.setEmpleado(empleado);
        jornada.setConceptoLaboral(conceptoLaboral);

        return jornada;
    }
}
