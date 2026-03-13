package com.example.ProyectoS1_Julian.dto.response;

import com.example.ProyectoS1_Julian.modelo.TipoMovimiento;

import java.time.LocalDate;
import java.util.Date;

public record MovimientoResponseDTO(

        Long id,
        Date fecha,
        TipoMovimiento tipoMovimiento,
        UsuarioResponseDTO usuario,
        BodegaResponseDTO bodegaOrigen,
        BodegaResponseDTO bodegaDestino

) {
}