package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.AuditoriaRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.AuditoriaResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Auditoria;
import com.example.ProyectoS1_Julian.modelo.Operacion;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuditoriaMapper {

    /* entidad DTO */

    public AuditoriaResponseDTO entidadADTO(Auditoria auditoria,
                                            UsuarioResponseDTO usuarioDTO){

        if(auditoria == null) return null;

        return new AuditoriaResponseDTO(

                auditoria.getId(),
                auditoria.getEntidad(),
                auditoria.getOperacion().name(),
                auditoria.getFecha(),
                auditoria.getValorAnterior(),
                auditoria.getValorNuevo(),
                usuarioDTO

        );
    }

    /* DTO entidad */

    public Auditoria DTOAentidad(AuditoriaRequestDTO dto,
                                 Usuario usuario){

        if(dto == null || usuario == null) return null;

        Auditoria auditoria = new Auditoria();

        auditoria.setEntidad(dto.entidad());
        auditoria.setOperacion(Operacion.valueOf(dto.operacion()));
        auditoria.setFecha(LocalDateTime.now());
        auditoria.setUsuario(usuario);
        auditoria.setValorAnterior(dto.valorAnterior());
        auditoria.setValorNuevo(dto.valorNuevo());

        return auditoria;
    }

    /* actualizar */

    public void actualizarEntidadDesdeDTO(Auditoria auditoria,
                                          AuditoriaRequestDTO dto,
                                          Usuario usuario){

        if(dto == null || auditoria == null) return;

        auditoria.setEntidad(dto.entidad());
        auditoria.setOperacion(Operacion.valueOf(dto.operacion()));
        auditoria.setUsuario(usuario);
        auditoria.setValorAnterior(dto.valorAnterior());
        auditoria.setValorNuevo(dto.valorNuevo());

    }

}