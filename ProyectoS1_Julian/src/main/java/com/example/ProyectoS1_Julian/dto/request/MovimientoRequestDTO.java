package com.example.ProyectoS1_Julian.dto.request;

import com.example.ProyectoS1_Julian.modelo.TipoMovimiento;

import java.time.LocalDate;

public record MovimientoRequestDTO(
        LocalDate fecha,
        String tipoMoviento,
        Long idUsuario,
        Long idBodegaOrigen,
        Long idBodegaDestino
) {
    public TipoMovimiento tipoMovimiento() {
        return null;
    }
}
