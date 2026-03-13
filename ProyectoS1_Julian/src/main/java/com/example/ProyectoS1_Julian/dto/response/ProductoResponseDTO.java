package com.example.ProyectoS1_Julian.dto.response;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String categoria,
        Double precio,
        Integer stock,
        BodegaResponseDTO bodega
) {
}
