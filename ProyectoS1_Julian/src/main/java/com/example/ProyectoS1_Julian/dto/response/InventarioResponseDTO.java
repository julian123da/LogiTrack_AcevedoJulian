package com.example.ProyectoS1_Julian.dto.response;

public record InventarioResponseDTO(

        Long id,
        Long bodegaId,
        Long productoId,
        Integer cantidad

) {
}