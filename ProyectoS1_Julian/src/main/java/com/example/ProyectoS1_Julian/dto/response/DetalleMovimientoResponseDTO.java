package com.example.ProyectoS1_Julian.dto.response;

public record DetalleMovimientoResponseDTO(
        Long id,
        Integer cantidad,
        MovimientoResponseDTO movimiento,
        ProductoResponseDTO producto

) {}