package com.example.ProyectoS1_Julian.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record InventarioRequestDTO(

        @Schema(description = "ID de la bodega", example = "1")
        Long idBodega,

        @Schema(description = "ID del producto", example = "2")
        Long idProducto,

        @Schema(description = "Cantidad del producto en la bodega", example = "50")
        Integer cantidad

) {
}