package com.example.ProyectoS1_Julian.dto.response;

public record DetalleMovimientoResponseDTO(
        Long id,
        Long movimientoId,
        Long productoId,
        Integer cantidad
) {}