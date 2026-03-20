package com.example.S1_Proyecto.controller;


import com.example.S1_Proyecto.dto.request.MovimientoRequestDTO;
import com.example.S1_Proyecto.dto.response.MovimientoResponseDTO;
import com.example.S1_Proyecto.service.MovimientoService;
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

@Tag(name = "Movimiento", description = "Contiene todo lo relacionado con Movimientos")
@RestController
@RequestMapping("/api/movimiento")
@RequiredArgsConstructor
@Validated
public class MovimientoController {

    private final MovimientoService movimientoService;

    @Operation(summary = "Crear Movimiento", description = "Permite registrar un nuevo movimiento")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimiento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o estructura incorrecta", content = @Content)
    })
    public ResponseEntity<MovimientoResponseDTO> crearMovimiento(@RequestBody MovimientoRequestDTO dto) {
        MovimientoResponseDTO creado = movimientoService.crearMovimiento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar Movimiento", description = "Permite actualizar un movimiento existente")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    public ResponseEntity<MovimientoResponseDTO> actualizarMovimiento(
            @RequestBody MovimientoRequestDTO dto,
            @Parameter(description = "ID del movimiento a actualizar", example = "1")
            @PathVariable Long id) {
        MovimientoResponseDTO actualizado = movimientoService.actualizarMovimiento(dto, id);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Listar Movimientos", description = "Obtiene la lista de todos los movimientos")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de movimientos obtenida correctamente")
    })
    public ResponseEntity<List<MovimientoResponseDTO>> listarMovimientos() {
        List<MovimientoResponseDTO> lista = movimientoService.listarMovimientos();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar Movimiento por ID", description = "Obtiene un movimiento específico por su ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado", content = @Content)
    })
    public ResponseEntity<MovimientoResponseDTO> buscarPorId(
            @Parameter(description = "ID del movimiento", example = "1")
            @PathVariable Long id) {
        MovimientoResponseDTO movimiento = movimientoService.buscarPorId(id);
        return ResponseEntity.ok(movimiento);
    }

    @Operation(summary = "Eliminar Movimiento", description = "Permite eliminar un movimiento por su ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movimiento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Movimiento no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminarMovimiento(
            @Parameter(description = "ID del movimiento a eliminar", example = "1")
            @PathVariable Long id) {
        movimientoService.eliminarMovimiento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/recientes")
    public ResponseEntity<List<MovimientoResponseDTO>> Receintes(){
        List<MovimientoResponseDTO> reciente = movimientoService.Recientes();
        return ResponseEntity.ok(reciente);
    }
    

}