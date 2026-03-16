package com.example.S1_Proyecto.dto.request;


import com.example.S1_Proyecto.modelo.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(

        @Schema(
                description = "Se ingresa el nombre del usuario",
                example = "Julian"
        )
        @NotBlank(message = "El nombre no puede estar vacio.")
        @Size(min = 2, max = 25, message = "El nombre debe tener entre 2 y 25 caracteres")
        String nombre,

        @Schema(
                description = "Documento del usuario",
                example = "1007999211"
        )
        String documento,

        @Schema(
                description = "Username del usuario",
                example = "julian_admin"
        )
        @NotBlank(message = "El usuario no puede estar vacio.")
        String username,

        @Schema(
                description = "Contraseña del usuario",
                example = "123456"
        )
        @NotBlank(message = "La contraseña no puede estar vacia.")
        String password,

        @Schema(
                description = "Rol del usuario",
                example = "ADMIN"
        )
        Rol rol

) {
}