package com.example.ProyectoS1_Julian.dto.response;

import java.time.LocalDate;

public record MovimientoResponseDTO(
        Long id,
        LocalDate fecha,
        String tipoMovimiento,
        Long idUsuario,
        Long idBodegaOrigen,
        Long idBodegaDestino
) {
}
