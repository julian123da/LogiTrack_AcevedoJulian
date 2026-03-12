package com.example.ProyectoS1_Julian.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AuditoriaResponseDTO(
        Long id,
        String entidad,
        String operacion,
        LocalDateTime fecha,
        Long idUsuario,
        String valorAnterior,
        String valorNuevo
) {
}
