package com.example.S1_Proyecto.dto.response;

public record DetalleMovimientoResponseDTO(
        Long id,
        Integer cantidad,
        MovimientoResponseDTO movimiento,
        ProductoResponseDTO producto

) {}