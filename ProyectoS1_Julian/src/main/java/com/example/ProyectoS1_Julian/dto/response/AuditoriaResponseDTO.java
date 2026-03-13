package com.example.ProyectoS1_Julian.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

public record AuditoriaResponseDTO(

        Long id,
        String entidad,
        String operacion,
        Date fecha,
        UsuarioResponseDTO usuario

) {
}