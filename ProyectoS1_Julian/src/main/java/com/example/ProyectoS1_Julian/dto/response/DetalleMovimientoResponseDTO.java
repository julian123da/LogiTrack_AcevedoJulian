package com.example.ProyectoS1_Julian.dto.response;

public record DetalleMovimientoResponseDTO(
        Long id,
        Long idMovimiento,
        Long idProducto,
        Integer cantidad
) {
}
