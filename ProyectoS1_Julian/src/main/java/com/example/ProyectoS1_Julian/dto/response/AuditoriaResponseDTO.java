package com.example.ProyectoS1_Julian.dto.response;

import java.time.LocalDateTime;

public record AuditoriaResponseDTO(

        Long id,
        String entidad,
        String operacion,
        LocalDateTime fecha,
        String valorAnterior,
        String valorNuevo,
        UsuarioResponseDTO usuario

) {
}