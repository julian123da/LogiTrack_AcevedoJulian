package com.example.S1_Proyecto.controller;


import com.example.S1_Proyecto.dto.request.DetalleMovimientoRequestDTO;
import com.example.S1_Proyecto.dto.response.DetalleMovimientoResponseDTO;
import com.example.S1_Proyecto.service.DetalleMovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DetalleMovimiento", description = "Gestión de los detalles de los movimientos")
@RestController
@RequestMapping("/api/detalleMovimiento")
@RequiredArgsConstructor
@Validated
public class DetalleMovimientoController {

    private final DetalleMovimientoService detalleMovimientoService;

    @Operation(summary = "Crear DetalleMovimiento", description = "Permite crear un nuevo detalle de movimiento")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "DetalleMovimiento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos", content = @Content)
    })
    public ResponseEntity<DetalleMovimientoResponseDTO> crearDetalle(@RequestBody DetalleMovimientoRequestDTO dto) {
        DetalleMovimientoResponseDTO creado = detalleMovimientoService.crearDetalleMovimiento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar DetalleMovimiento", description = "Actualiza un detalle de movimiento existente")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DetalleMovimiento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "DetalleMovimiento no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<DetalleMovimientoResponseDTO> actualizarDetalle(
            @RequestBody DetalleMovimientoRequestDTO dto,
            @Parameter(description = "ID del detalle a actualizar", example = "1") @PathVariable Long id) {
        DetalleMovimientoResponseDTO actualizado = detalleMovimientoService.actualizarDetalleMovimiento(dto, id);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Listar DetallesMovimiento", description = "Obtiene todos los detalles de los movimientos")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de DetallesMovimiento obtenida correctamente")
    })
    public ResponseEntity<List<DetalleMovimientoResponseDTO>> listarDetalles() {
        return ResponseEntity.ok().body(detalleMovimientoService.listarDetallesMovimiento());
    }

    @Operation(summary = "Buscar DetalleMovimiento por ID", description = "Obtiene un detalle de movimiento por su ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DetalleMovimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "DetalleMovimiento no encontrado", content = @Content)
    })
    public ResponseEntity<DetalleMovimientoResponseDTO> buscarPorId(
            @Parameter(description = "ID del detalle a buscar", example = "1") @PathVariable Long id) {
        DetalleMovimientoResponseDTO encontrado = detalleMovimientoService.buscarPorId(id);
        return ResponseEntity.ok(encontrado);
    }

    @Operation(summary = "Eliminar DetalleMovimiento", description = "Elimina un detalle de movimiento por su ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "DetalleMovimiento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "DetalleMovimiento no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminarDetalle(
            @Parameter(description = "ID del detalle a eliminar", example = "1") @PathVariable Long id) {
        detalleMovimientoService.eliminarDetalleMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}