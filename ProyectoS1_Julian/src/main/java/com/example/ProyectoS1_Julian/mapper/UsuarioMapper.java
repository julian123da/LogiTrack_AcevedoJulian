package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.UsuarioRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Rol;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto){

        if (dto == null) return null;
        Usuario usuario = new Usuario();

        usuario.setNombre(dto.nombre());
        usuario.setDocumento(dto.documento());
        usuario.setRol(Rol.valueOf(dto.rol()));
        usuario.getUsuario(dto.usuario());
        usuario.setPassword(dto.password());

        return usuario;
    }
    public UsuarioResponseDTO toDTO(Usuario usuario){
        if(usuario == null) return null;

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getDocumento(),
                usuario.getRol().name(),
                usuario.getUsuario()
        );
    }
}
