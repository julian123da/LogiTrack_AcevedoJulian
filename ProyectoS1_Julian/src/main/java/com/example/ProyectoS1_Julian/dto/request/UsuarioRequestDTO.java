package com.example.ProyectoS1_Julian.dto.request;

public record UsuarioRequestDTO(

        String nombre,
        String documento,
        String rol,
        String usuario,
        String password
) {
}
