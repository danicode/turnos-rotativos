package com.gestor.turnos_rotativos.controller;

import com.gestor.turnos_rotativos.dto.JornadaRequestDTO;
import com.gestor.turnos_rotativos.dto.JornadaResponseDTO;
import com.gestor.turnos_rotativos.service.JornadaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class JornadaController {

    private final JornadaService service;

    @GetMapping("/jornada")
    public ResponseEntity<List<JornadaResponseDTO>> get() {
        return ResponseEntity.ok(service.get());
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
