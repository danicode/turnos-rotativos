package com.gestor.turnos_rotativos.service;

import com.gestor.turnos_rotativos.dto.JornadaRequestDTO;
import com.gestor.turnos_rotativos.dto.JornadaResponseDTO;
import com.gestor.turnos_rotativos.entity.ConceptoLaboral;
import com.gestor.turnos_rotativos.entity.Empleado;
import com.gestor.turnos_rotativos.entity.Jornada;
import com.gestor.turnos_rotativos.mapper.GenericExtendedMapper;
import com.gestor.turnos_rotativos.repository.JornadaRepository;
import com.gestor.turnos_rotativos.service.impl.JornadaServiceImpl;
import com.gestor.turnos_rotativos.validator.JornadaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class JornadaServiceImplTest {

    @Mock
    private JornadaRepository jornadaRepository;
    @Mock
    private EmpleadoService empleadoService;
    @Mock
    private ConceptoLaboralService conceptoLaboralService;
    @Mock
    private JornadaValidator jornadaValidator;
    @Mock
    private GenericExtendedMapper<Jornada, JornadaResponseDTO, JornadaRequestDTO, Empleado, ConceptoLaboral> mapper;

    @InjectMocks
    private JornadaServiceImpl jornadaService;

    private JornadaRequestDTO jornadaRequestDTO;
    private JornadaResponseDTO jornadaResponseDTO;
    private Jornada jornada;
    private Empleado empleado;
    private ConceptoLaboral conceptoLaboral;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simulación de datos
        empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNroDocumento(12345678);
        empleado.setNombre("Juan");
        empleado.setApellido("Perez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        empleado.setFechaIngreso(LocalDate.of(2020, 1, 1));

        conceptoLaboral = new ConceptoLaboral();
        conceptoLaboral.setId(1);
        conceptoLaboral.setHsMaximo(8);
        conceptoLaboral.setHsMinimo(6);
        conceptoLaboral.setLaborable(true);
        conceptoLaboral.setNombre("Turno Normal");

        jornadaRequestDTO = new JornadaRequestDTO();
        jornadaRequestDTO.setIdEmpleado(1L);
        jornadaRequestDTO.setIdConcepto(1);
        jornadaRequestDTO.setFecha(LocalDate.now());
        jornadaRequestDTO.setHorasTrabajadas(8);

        jornada = new Jornada();
        jornada.setId(1L);
        jornada.setEmpleado(empleado);
        jornada.setConceptoLaboral(conceptoLaboral);
        jornada.setFecha(jornadaRequestDTO.getFecha());
        jornada.setHorasTrabajadas(8);

        jornadaResponseDTO = new JornadaResponseDTO();
        jornadaRequestDTO.setIdEmpleado(1L);
        jornadaRequestDTO.setIdConcepto(1);
        jornadaRequestDTO.setFecha(LocalDate.now());
        jornadaRequestDTO.setHorasTrabajadas(8);
    }

    /**
     * Importante: Esta prueba no funciona. No pude mockear correctamente los servicios de empleado y concepto
     */
    @DisplayName("Test: Crear Jornada")
    @Test
    void testCrearJornada() {
        //given
        given(empleadoService.getEntityById(1L)).willReturn(empleado);
        given(conceptoLaboralService.getEntityById(1)).willReturn(conceptoLaboral);
        given(mapper.toEntity(jornadaRequestDTO, empleado, conceptoLaboral)).willReturn(jornada); // Convierte el DTO en la entidad
        given(jornadaRepository.save(jornada)).willReturn(jornada); // Simula la persistencia
        given(mapper.toDto(jornada)).willReturn(jornadaResponseDTO); // Convierte la entidad en DTO

        //when
        JornadaResponseDTO response = jornadaService.create(jornadaRequestDTO);

        //then
        assertThat(response).isNotNull();
    }

    /**
     * Importante: aquí tampoco funciona el test debido a que considero que tengo que mockear más funcionalidades
     *  que se realizan en jornadaService.get
     * */
    @DisplayName("Test: Obtener todas las Jornadas sin parámetros")
    @Test
    void obtenerTodasLasJornadasSinQueryParams() {
        // Mockear la lista de jornadas
        List<Jornada> jornadas = List.of(jornada); // Simular dos jornadas
        given(jornadaRepository.findAll()).willReturn(List.of(jornada));

        // Ejecutar el servicio sin Query Params
        List<JornadaResponseDTO> response = jornadaService.get(null, null, null);

        // Verificar que se devuelvan todas las jornadas
        assertThat(response).isNotEmpty();
        assertEquals(1, response.size());
    }
}
