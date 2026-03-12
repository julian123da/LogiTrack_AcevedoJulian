package com.example.ProyectoS1_Julian.dto.request;

public record DetalleMovimientoRequestDTO(
        Long idMovimiento,
        Long idProducto,
        Integer cantidad
) {
}
