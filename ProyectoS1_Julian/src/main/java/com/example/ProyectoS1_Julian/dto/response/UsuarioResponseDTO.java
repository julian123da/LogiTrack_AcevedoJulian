package com.example.ProyectoS1_Julian.dto.response;

public record UsuarioResponseDTO(
        long id,
        String nombre,
        String documento,
        String rol,
        String usuario
) {
}
