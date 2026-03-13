package com.example.ProyectoS1_Julian.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Date;

public record AuditoriaRequestDTO(

        @Schema(description = "Entidad afectada", example = "producto")
        String entidad,

        @Schema(description = "Tipo de operación realizada", example = "UPDATE")
        String operacion,

        @Schema(description = "Fecha de la operación", example = "2026-03-12T10:30:00")
        Date fecha,

        @Schema(description = "ID del usuario que realizó la acción", example = "1")
        Long idUsuario

) {
}