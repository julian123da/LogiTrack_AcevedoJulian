package com.example.S1_Proyecto.dto.response;



import com.example.S1_Proyecto.modelo.TipoMovimiento;

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