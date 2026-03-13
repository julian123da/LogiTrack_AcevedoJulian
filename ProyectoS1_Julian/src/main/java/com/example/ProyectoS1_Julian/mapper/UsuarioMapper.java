package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.UsuarioRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Rol;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {


    /* entidad DTO */

    public UsuarioResponseDTO entidadADTO(Usuario usuario){

        if(usuario == null) return null;

        return new UsuarioResponseDTO(
                usuario.getId(), usuario.getNombre(), usuario.getDocumento(),
                usuario.getUsername(), usuario.getPassword(),usuario.getRol()

        );
    }

    /* DTO entidad */

    public Usuario DTOAentidad(UsuarioRequestDTO dto){

        if(dto == null) return null;

        Usuario u = new Usuario();
        u.setNombre(dto.nombre());
        u.setDocumento(dto.documento());
        u.setUsername(dto.username());
        u.setPassword(dto.password());
        u.setRol(dto.rol());
        return u;
    }


    /* actualizar entidad */

    public void actualizarEntidadDesdeDTO(Usuario usuario,
                                          UsuarioRequestDTO dto){

        if(dto == null || usuario == null) return;
        usuario.setNombre(dto.nombre());
        usuario.setDocumento(dto.documento());
        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());
        usuario.setRol(dto.rol());
    }

}