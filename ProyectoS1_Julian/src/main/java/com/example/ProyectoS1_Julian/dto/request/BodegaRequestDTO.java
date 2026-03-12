package com.example.ProyectoS1_Julian.dto.request;

public record BodegaRequestDTO(
        String nombre,
        String ubicacion,
        Integer capacidad,
        Long idEncargado
) {
}
