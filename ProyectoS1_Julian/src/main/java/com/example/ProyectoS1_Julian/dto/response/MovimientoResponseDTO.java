package com.example.ProyectoS1_Julian.dto.response;

import com.example.ProyectoS1_Julian.modelo.TipoMovimiento;

import java.time.LocalDate;

public record MovimientoResponseDTO(

        Long id,
        LocalDate fecha,
        TipoMovimiento tipoMovimiento,
        UsuarioResponseDTO usuario,
        BodegaResponseDTO bodegaOrigen,
        BodegaResponseDTO bodegaDestino

) {
}