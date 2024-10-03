package com.gestor.turnos_rotativos.service;

import com.gestor.turnos_rotativos.dto.EmpleadoDTO;
import com.gestor.turnos_rotativos.entity.Empleado;
import com.gestor.turnos_rotativos.exception.BusinessRuleFieldException;
import com.gestor.turnos_rotativos.repository.EmpleadoRepository;
import com.gestor.turnos_rotativos.repository.JornadaRepository;
import com.gestor.turnos_rotativos.service.impl.EmpleadoServiceImpl;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    @Mock
    private JornadaRepository jornadaRepository;

    private EmpleadoDTO empleadoDTO;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNroDocumento(12345678);
        empleadoDTO.setNombre("Juan");
        empleadoDTO.setApellido("Perez");
        empleadoDTO.setEmail("juan.perez@example.com");
        empleadoDTO.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        empleadoDTO.setFechaIngreso(LocalDate.of(2020, 1, 1));

        empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNroDocumento(12345678);
        empleado.setNombre("Juan");
        empleado.setApellido("Perez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        empleado.setFechaIngreso(LocalDate.of(2020, 1, 1));
    }

    @DisplayName("Test: Crear empleado correctamente")
    @Test
    void altaEmpleadoCorrectamente() {
        // Mock para el repository
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);

        // Llamada al servicio
        Long id = empleadoService.create(empleadoDTO);

        // Verificaciones
        assertEquals(empleado.getId(), id);
        verify(empleadoRepository).save(any(Empleado.class));
    }

    @DisplayName("Test: Error cuando el empleado es menor de 18 años")
    @Test
    void altaEmpleadoMenorDeEdadLanzaError() {
        // Cambiar la fecha de nacimiento para que el empleado sea menor de 18 años
        empleadoDTO.setFechaNacimiento(LocalDate.now().minusYears(16));

        // Ejecutar y verificar excepción
        BusinessRuleFieldException exception = assertThrows(BusinessRuleFieldException.class, () -> {
            empleadoService.create(empleadoDTO);
        });

        assertEquals("La edad del empleado no puede ser menor a 18 años.", exception.getMessage());
    }

    @DisplayName("Test: Error cuando el documento ya existe")
    @Test
    void altaEmpleadoConNroDocumentoDuplicadoLanzaError() {
        // Simular que ya existe un empleado con ese número de documento
        when(empleadoRepository.save(any(Empleado.class))).thenThrow(new DataIntegrityViolationException("Ya existe un empleado con el documento ingresado."));

        // Ejecutar y verificar
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            empleadoService.create(empleadoDTO);
        });

        assertEquals("Ya existe un empleado con el documento ingresado.", exception.getMessage());
    }

    @DisplayName("Test: Error cuando el email ya existe")
    @Test
    void altaEmpleadoConEmailDuplicadoLanzaError() {
        // Simular que ya existe un empleado con ese email
        when(empleadoRepository.save(any(Empleado.class))).thenThrow(new DataIntegrityViolationException("Ya existe un empleado con el email ingresado."));

        // Cambiar el email en empleadoDTO a uno duplicado
        empleadoDTO.setEmail("juan.perez@email.com");

        // Ejecutar y verificar excepción
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            empleadoService.create(empleadoDTO);
        });

        assertEquals("Ya existe un empleado con el email ingresado.", exception.getMessage());
    }

    @DisplayName("Test: Error cuando la fecha de ingreso es futura")
    @Test
    void altaEmpleadoConFechaIngresoFuturaLanzaError() {
        // Cambiar la fecha de ingreso a una fecha futura
        empleadoDTO.setFechaIngreso(LocalDate.now().plusDays(1));
        when(empleadoRepository.save(any(Empleado.class))).thenThrow(new ValidationException("La fecha de ingreso no puede ser posterior al día de la fecha."));

        // Ejecutar y verificar excepción
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            empleadoService.create(empleadoDTO);
        });

        assertEquals("La fecha de ingreso no puede ser posterior al día de la fecha.", exception.getMessage());
    }

    @DisplayName("Test: Error cuando el email no es válido")
    @Test
    void createEmpleadoConEmailInvalidoLanzaError() {
        // Cambiar el email a uno no válido
        empleadoDTO.setEmail("email_invalido");
        when(empleadoRepository.save(any(Empleado.class))).thenThrow(new ValidationException("El email ingresado no es correcto."));

        // Ejecutar y verificar excepción de validación
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            empleadoService.create(empleadoDTO);
        });

        assertTrue(exception.getMessage().contains("El email ingresado no es correcto."));
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void obtenerEmpleados() {
        //given
        Empleado empleado1 = new Empleado();
        empleado1.setId(2L);
        empleado1.setNombre("Julen");
        empleado1.setApellido("Oliva");
        empleado1.setEmail("j2@gmail.com");
        given(empleadoRepository.findAll()).willReturn(List.of(empleado, empleado1));

        //when
        List<EmpleadoDTO> empleados = empleadoService.get();

        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    void obtenerColeccionEmpleadosVacia() {
        //given
        given(empleadoRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<EmpleadoDTO> listaEmpleados = empleadoService.get();

        //then
        assertThat(listaEmpleados).isEmpty();
        assertThat(listaEmpleados.size()).isEqualTo(0);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void obtenerEmpleadoPorId() {

        //given
        given(empleadoRepository.findById(1L)).willReturn(Optional.ofNullable(empleado));

        //when
        EmpleadoDTO empleadoGuardado = empleadoService.getById(empleado.getId());

        //then
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void actualizarEmpleado() {
        // Given
        Long empleadoId = 1L;
        empleadoDTO.setEmail("chr2@gmail.com");
        empleadoDTO.setNombre("Christian Raul");

        // Simular que el empleado existe y será actualizado
        when(empleadoRepository.findById(empleadoId)).thenReturn(Optional.of(empleado));
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);

        // When
        EmpleadoDTO empleadoActualizado = empleadoService.update(empleadoId, empleadoDTO);

        // Then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("chr2@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Christian Raul");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void eliminarEmpleado() {
        //given
        long empleadoId = 1L;
        given(empleadoRepository.findById(empleadoId)).willReturn(Optional.of(empleado));
        willDoNothing().given(empleadoRepository).deleteById(empleadoId);

        //when
        empleadoService.delete(empleadoId);

        //then
        verify(empleadoRepository, times(1)).deleteById(empleadoId);
    }
}
