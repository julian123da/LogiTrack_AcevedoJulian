package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.UsuarioRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Rol;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    /* DTO entidad */

    public Usuario DTOAentidad(UsuarioRequestDTO dto){

        if(dto == null) return null;

        Usuario newusuario = new Usuario();

        newusuario.setNombre(dto.nombre());
        newusuario.setDocumento(dto.documento());
        newusuario.setRol(Rol.valueOf(dto.rol()));
        newusuario.setUsername(dto.username());
        newusuario.setPassword(dto.password());

        return newusuario;
    }

    /* entidad DTO */

    public UsuarioResponseDTO entidadADTO(Usuario usuario){

        if(usuario == null) return null;

        return new UsuarioResponseDTO(

                usuario.getId(),
                usuario.getNombre(),
                usuario.getDocumento(),
                usuario.getRol(),
                usuario.getUsername(),
                usuario.getPassword()

        );
    }

    /* actualizar entidad */

    public void actualizarEntidadDesdeDTO(Usuario usuario,
                                          UsuarioRequestDTO dto){

        if(dto == null || usuario == null) return;

        usuario.setNombre(dto.nombre());
        usuario.setDocumento(dto.documento());
        usuario.setRol(Rol.valueOf(dto.rol()));
        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());

    }

}