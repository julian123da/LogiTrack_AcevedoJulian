package com.example.S1_Proyecto.mapper;


import com.example.S1_Proyecto.dto.request.UsuarioRequestDTO;
import com.example.S1_Proyecto.dto.response.UsuarioResponseDTO;
import com.example.S1_Proyecto.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO entidadADTO(Usuario usuario) {
        if (usuario == null) return null;

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getDocumento(),
                usuario.getUsername(),
                // password omitida intencionalmente
                usuario.getRol()
        );
    }

    public Usuario DTOAentidad(UsuarioRequestDTO dto) {
        if (dto == null) return null;

        Usuario u = new Usuario();
        u.setNombre(dto.nombre());
        u.setDocumento(dto.documento());
        u.setUsername(dto.username());
        u.setPassword(dto.password()); // se encripta en el service antes de persistir
        u.setRol(dto.rol());
        return u;
    }

    public void actualizarEntidadDesdeDTO(Usuario usuario, UsuarioRequestDTO dto) {
        if (dto == null || usuario == null) return;
        usuario.setNombre(dto.nombre());
        usuario.setDocumento(dto.documento());
        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password()); // se encripta en el service antes de persistir
        usuario.setRol(dto.rol());
    }
}