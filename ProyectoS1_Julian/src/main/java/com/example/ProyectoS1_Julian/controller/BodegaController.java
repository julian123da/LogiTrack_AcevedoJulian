package com.example.ProyectoS1_Julian.controller;

import com.example.ProyectoS1_Julian.service.BodegaService;
import com.example.ProyectoS1_Julian.dto.request.BodegaRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.BodegaResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bodega", description = "Contiene todo lo relacionado con las bodegas")
@RestController
@RequestMapping("/api/bodega")
@RequiredArgsConstructor
@Validated
public class BodegaController {

    private final BodegaService bodegaService;

    @PostMapping
    public ResponseEntity<BodegaResponseDTO> crearBodega(@Valid @RequestBody BodegaRequestDTO dto) {
        BodegaResponseDTO respuesta = bodegaService.crearBodega(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BodegaResponseDTO> actualizarBodega(@Valid @RequestBody BodegaRequestDTO dto,
                                                              @PathVariable Long id) {
        BodegaResponseDTO respuesta = bodegaService.actualizarBodega(dto, id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<BodegaResponseDTO>> listarBodegas() {
        List<BodegaResponseDTO> lista = bodegaService.listarBodegas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodegaResponseDTO> buscarBodegaPorId(@PathVariable Long id) {
        BodegaResponseDTO respuesta = bodegaService.buscarPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBodega(@PathVariable Long id) {
        bodegaService.eliminarBodega(id);
        return ResponseEntity.noContent().build();
    }

}