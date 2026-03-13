package com.example.ProyectoS1_Julian.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductoRequestDTO(

        @Schema(description = "Nombre del producto", example = "Laptop Lenovo")
        String nombre,

        @Schema(description = "Categoría del producto", example = "Tecnologia")
        String categoria,

        @Schema(description = "Precio del producto", example = "2500.00")
        double precio,

        @Schema(description = "Cantidad total en stock", example = "10")
        Integer stock,

        @Schema( description = "Id bodega donde se almacena el producto")
        Long bodegaId

) {
}