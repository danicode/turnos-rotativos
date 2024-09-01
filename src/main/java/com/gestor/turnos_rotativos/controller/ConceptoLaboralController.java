package com.gestor.turnos_rotativos.controller;

import com.gestor.turnos_rotativos.dto.ConceptoLaboralDTO;
import com.gestor.turnos_rotativos.service.ConceptoLaboralService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ConceptoLaboralController {

    private final ConceptoLaboralService service;

    @GetMapping("/concepto-laboral")
    public ResponseEntity<List<ConceptoLaboralDTO>> get(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "nombre", required = false) String nombre) {

        return ResponseEntity.ok(service.get(id, nombre));
    }
}
