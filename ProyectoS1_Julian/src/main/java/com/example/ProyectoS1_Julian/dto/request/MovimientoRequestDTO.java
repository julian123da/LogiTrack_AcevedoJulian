package com.example.ProyectoS1_Julian.dto.request;

import com.example.ProyectoS1_Julian.modelo.TipoMovimiento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;

public record MovimientoRequestDTO(

        @Schema (description = "Fecha del movimiento")
        Date fecha,

        @Schema(description = "Tipo de movimiento", example = "entrada")
        TipoMovimiento tipoMovimiento,

        @Schema(description = "ID del usuario responsable", example = "1")
        Long usuarioId,

        @Schema(description = "ID de la bodega origen", example = "1")
        Long bodegaOrigenId,

        @Schema(description = "ID de la bodega destino", example = "2")
        Long bodegaDestinoId

) {
}