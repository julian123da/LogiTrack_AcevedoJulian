package com.example.ProyectoS1_Julian.controller;

import com.example.ProyectoS1_Julian.dto.request.AuditoriaRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.AuditoriaResponseDTO;
import com.example.ProyectoS1_Julian.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Auditoría", description = "Operaciones relacionadas con Auditorías")
@RestController
@RequestMapping("/api/auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @Operation(summary = "Crear Auditoría", description = "Registra una nueva auditoría en el sistema")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Auditoría creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<AuditoriaResponseDTO> crearAuditoria(@RequestBody AuditoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditoriaService.crearAuditoria(dto));
    }

    @Operation(summary = "Actualizar Auditoría", description = "Actualiza una auditoría existente mediante su ID")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Auditoría actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Auditoría no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<AuditoriaResponseDTO> actualizarAuditoria(@RequestBody AuditoriaRequestDTO dto,
                                                                    @PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.actualizarAuditoria(dto, id));
    }

    @Operation(summary = "Listar Auditorías", description = "Obtiene todas las auditorías registradas")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de auditorías obtenida correctamente")
    })
    public ResponseEntity<List<AuditoriaResponseDTO>> listarAuditorias() {
        return ResponseEntity.ok(auditoriaService.listarAuditorias());
    }

    @Operation(summary = "Buscar Auditoría por ID", description = "Obtiene una auditoría específica mediante su ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Auditoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Auditoría no encontrada")
    })
    public ResponseEntity<AuditoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(auditoriaService.buscarPorId(id));
    }

    @Operation(summary = "Eliminar Auditoría", description = "Elimina una auditoría mediante su ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Auditoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Auditoría no encontrada")
    })
    public ResponseEntity<Void> eliminarAuditoria(@PathVariable Long id) {
        auditoriaService.eliminarAuditoria(id);
        return ResponseEntity.noContent().build();
    }
}