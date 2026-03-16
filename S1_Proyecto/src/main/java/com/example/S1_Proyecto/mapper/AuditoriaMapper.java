package com.example.S1_Proyecto.mapper;


import com.example.S1_Proyecto.dto.request.AuditoriaRequestDTO;
import com.example.S1_Proyecto.dto.response.AuditoriaResponseDTO;
import com.example.S1_Proyecto.dto.response.UsuarioResponseDTO;
import com.example.S1_Proyecto.modelo.Auditoria;
import com.example.S1_Proyecto.modelo.Usuario;
import org.springframework.stereotype.Component;

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
                usuarioDTO

        );
    }

    /* DTO entidad */

    public Auditoria DTOAentidad(AuditoriaRequestDTO dto,
                                 Usuario usuario){

        if(dto == null || usuario == null) return null;

        Auditoria auditoria = new Auditoria();




        return auditoria;
    }

    /* actualizar */

    public void actualizarEntidadDesdeDTO(Auditoria auditoria,
                                          AuditoriaRequestDTO dto,
                                          Usuario usuario){

        if(dto == null || auditoria == null) return;



    }

}