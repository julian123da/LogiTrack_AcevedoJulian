package com.example.ProyectoS1_Julian.dto.response;

public record BodegaResponseDTO(

        Long id,
        String nombre,
        String ubicacion,
        Integer capacidad,
        UsuarioResponseDTO usuario

) {
}