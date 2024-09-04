package com.gestor.turnos_rotativos.controller;

import com.gestor.turnos_rotativos.dto.EmpleadoDTO;
import com.gestor.turnos_rotativos.service.EmpleadoService;
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
public class EmpleadoController {

    private final EmpleadoService service;

    @GetMapping("/empleados")
    public ResponseEntity<List<EmpleadoDTO>> get() {
        return ResponseEntity.ok(service.get());
    }

    @PostMapping("/empleados")
    public ResponseEntity<EmpleadoDTO> create(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        Long id = service.create(empleadoDTO);
        return ResponseEntity.created(URI.create("/empleados/" + id))
                .body(empleadoDTO);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> getBydId(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<EmpleadoDTO> update(@NotNull @PathVariable Long id, @Valid @RequestBody EmpleadoDTO empleadoDTO) {
        return ResponseEntity.ok(service.update(id, empleadoDTO));
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
