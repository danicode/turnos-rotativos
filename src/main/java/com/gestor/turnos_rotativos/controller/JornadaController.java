package com.gestor.turnos_rotativos.controller;

import com.gestor.turnos_rotativos.dto.JornadaRequestDTO;
import com.gestor.turnos_rotativos.dto.JornadaResponseDTO;
import com.gestor.turnos_rotativos.service.JornadaService;
import com.gestor.turnos_rotativos.validator.JornadaValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class JornadaController {

    private final JornadaService service;
    private final JornadaValidator jornadaValidator;

    @GetMapping("/jornada")
    public ResponseEntity<List<JornadaResponseDTO>> get(
            @RequestParam(required = false) String nroDocumento,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta) {

        jornadaValidator.validateJornadaRequestParam(nroDocumento, fechaDesde, fechaHasta);

        Integer nroDocumentoValidated = parseInteger(nroDocumento);
        LocalDate fechaDesdeValidated = parseLocalDate(fechaDesde);
        LocalDate fechaHastaValidated = parseLocalDate(fechaHasta);

        List<JornadaResponseDTO> jornadas = service.get(nroDocumentoValidated, fechaDesdeValidated, fechaHastaValidated);

        return ResponseEntity.ok(jornadas);
    }

    private Integer parseInteger(String value) {
        return value == null ? null : Integer.valueOf(value);
    }

    private LocalDate parseLocalDate(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    @PostMapping("/jornada")
    public ResponseEntity<JornadaResponseDTO> create(@Valid @RequestBody JornadaRequestDTO jornadaRequestDTO) {
        JornadaResponseDTO jornadaResponseDTO = service.create(jornadaRequestDTO);
        return ResponseEntity.created(URI.create("/jornada/" + jornadaResponseDTO.getId()))
                .body(jornadaResponseDTO);
    }

    @GetMapping("/jornada/{id}")
    public ResponseEntity<JornadaResponseDTO> getBydId(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
