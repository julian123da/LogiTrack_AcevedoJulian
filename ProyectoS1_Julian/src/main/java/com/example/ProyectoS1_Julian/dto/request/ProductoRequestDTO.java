package com.example.ProyectoS1_Julian.dto.request;

public record ProductoRequestDTO(
        String nombre,
        String categoria,
        String precio,
        Integer stockTotal
) {
}
