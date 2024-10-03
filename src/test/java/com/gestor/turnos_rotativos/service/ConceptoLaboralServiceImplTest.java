package com.gestor.turnos_rotativos.service;

import com.gestor.turnos_rotativos.dto.ConceptoLaboralDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import com.gestor.turnos_rotativos.exception.BusinessException;
import com.gestor.turnos_rotativos.repository.ConceptoLaboralRepository;
import com.gestor.turnos_rotativos.service.impl.ConceptoLaboralServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConceptoLaboralServiceImplTest {

    @Mock
    private ConceptoLaboralRepository repository;

    @InjectMocks
    private ConceptoLaboralServiceImpl service;

    private ConceptoLaboral concepto1;
    private ConceptoLaboral concepto2;
    private ConceptoLaboral concepto3;

    private ConceptoLaboralDTO conceptoDTO1;
    private ConceptoLaboralDTO conceptoDTO2;
    private ConceptoLaboralDTO conceptoDTO3;

    @BeforeEach
    void setUp() {
        concepto1 = new ConceptoLaboral();
        concepto1.setId(1);
        concepto1.setHsMaximo(8);
        concepto1.setHsMinimo(6);
        concepto1.setLaborable(true);
        concepto1.setNombre("Turno Normal");

        concepto2 = new ConceptoLaboral();
        concepto2.setId(2);
        concepto2.setHsMaximo(6);
        concepto2.setHsMinimo(2);
        concepto2.setLaborable(true);
        concepto2.setNombre("Turno Extra");

        concepto3 = new ConceptoLaboral();
        concepto3.setId(3);
        concepto3.setHsMaximo(null);
        concepto3.setHsMinimo(null);
        concepto3.setLaborable(false);
        concepto3.setNombre("Día Libre");

        conceptoDTO1 = new ConceptoLaboralDTO();
        conceptoDTO1.setHsMaximo(8);
        conceptoDTO1.setHsMinimo(6);
        conceptoDTO1.setLaborable(true);
        conceptoDTO1.setNombre("Turno Normal");

        conceptoDTO2 = new ConceptoLaboralDTO();
        conceptoDTO2.setHsMaximo(6);
        conceptoDTO2.setHsMinimo(2);
        conceptoDTO2.setLaborable(true);
        conceptoDTO2.setNombre("Turno Extra");

        conceptoDTO3 = new ConceptoLaboralDTO();
        conceptoDTO3.setHsMaximo(null);
        conceptoDTO3.setHsMinimo(null);
        conceptoDTO3.setLaborable(false);
        conceptoDTO3.setNombre("Día Libre");
    }

    @DisplayName("Test: Obtener Lista de Conceptos Laborales")
    @Test
    void obtenerListaConceptos() {
        // Datos simulados
        List<ConceptoLaboral> conceptos = List.of(concepto1, concepto2, concepto3);
        List<ConceptoLaboralDTO> conceptoDTOs = List.of(conceptoDTO1, conceptoDTO2, conceptoDTO3);

        when(repository.findAll()).thenReturn(conceptos);

        // Verificar el resultado
        List<ConceptoLaboralDTO> result = service.get(null, null);

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(repository, times(1)).findAll();
    }

    @DisplayName("Test: Obtener Concepto Laboral Por Id")
    @Test
    void obtenerConceptoPorId() {
        // Datos simulados
        List<ConceptoLaboral> conceptos = List.of(concepto1, concepto2, concepto3);
        List<ConceptoLaboralDTO> conceptoDTOs = List.of(conceptoDTO1, conceptoDTO2, conceptoDTO3);

        when(repository.findById(1)).thenReturn(Optional.of(concepto1));

        // Verificar el resultado
        List<ConceptoLaboralDTO> result = service.get(1, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findById(1);
    }

    @DisplayName("Test: Obtener Lista de Conceptos Laborales por Nombre")
    @Test
    void obtenerConceptoPorNombre() {
        // Datos simulados
        List<ConceptoLaboral> conceptos = List.of(concepto3);
        List<ConceptoLaboralDTO> conceptoDTOs = List.of(conceptoDTO3);

        when(repository.findByNombreContainingIgnoreCase("Día Libre")).thenReturn(conceptos);

        // Verificar el resultado
        List<ConceptoLaboralDTO> result = service.get(null, "Día Libre");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findByNombreContainingIgnoreCase("Día Libre");
    }

    @DisplayName("Test: Obtener Lista de Conceptos Laborales vacía")
    @Test
    void obtenerListaConceptosVacia() {
        when(repository.findAll()).thenReturn(List.of());

        List<ConceptoLaboralDTO> result = service.get(null, null);

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @DisplayName("Test: Lanzar error cuando no existe Concepto Laboral")
    @Test
    void lanzarExcepcionConceptoInexistente() {
        // Simular que no se encuentra el concepto
        when(repository.findById(99)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> service.getEntityById(99));

        assertEquals("No existe el concepto ingresado.", exception.getMessage());
        verify(repository, times(1)).findById(99);
    }
}
