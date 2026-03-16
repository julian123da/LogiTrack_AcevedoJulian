package com.example.S1_Proyecto.dto.response;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String categoria,
        Double precio,
        Integer stock,
        BodegaResponseDTO bodega
) {
}
