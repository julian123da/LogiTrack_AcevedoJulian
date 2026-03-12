package com.example.ProyectoS1_Julian.dto.request;

public record InventarioRequestDTO(
        Long idBodega,
        Long idProducto,
        Integer cantidad
) {
}
