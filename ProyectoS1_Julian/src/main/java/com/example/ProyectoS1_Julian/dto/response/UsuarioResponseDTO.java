package com.example.ProyectoS1_Julian.dto.response;

import com.example.ProyectoS1_Julian.modelo.Rol;

public record UsuarioResponseDTO(

        Long id,
        String nombre,
        String documento,
        Rol rol,
        String usuario,
        String password

) {
}