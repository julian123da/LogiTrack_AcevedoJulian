package com.example.S1_Proyecto.dto.response;


import com.example.S1_Proyecto.modelo.Rol;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String documento,
        String username,
        // La contraseña NO se devuelve nunca en las respuestas
        Rol rol
) {
}
