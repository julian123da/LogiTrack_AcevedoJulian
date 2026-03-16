package com.example.S1_Proyecto.dto.response;

public record BodegaResponseDTO(

        Long id,
        String nombre,
        String ubicacion,
        Integer capacidad,
        UsuarioResponseDTO usuario

) {
}