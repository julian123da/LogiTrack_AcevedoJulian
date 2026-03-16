package com.example.S1_Proyecto.dto.response;

import java.util.Date;

public record AuditoriaResponseDTO(

        Long id,
        String entidad,
        String operacion,
        Date fecha,
        UsuarioResponseDTO usuario

) {
}