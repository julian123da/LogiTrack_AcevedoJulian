package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.BodegaRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.BodegaResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Bodega;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class BodegaMapper {

    /* entidad → DTO */

    public BodegaResponseDTO entidadADTO(Bodega bodega, UsuarioResponseDTO dto){

        if(bodega == null) return null;

        return new BodegaResponseDTO(

                bodega.getId(),
                bodega.getNombre(),
                bodega.getUbicacion(),
                bodega.getCapacidad(),
                dto

        );

    }

    /* DTO entidad */

    public Bodega DTOAentidad(BodegaRequestDTO dto, Usuario usuario){

        if(dto == null || usuario == null) return null;

        Bodega bodega = new Bodega();

        bodega.setNombre(dto.nombre());
        bodega.setUbicacion(dto.ubicacion());
        bodega.setCapacidad(dto.capacidad());
        bodega.setUsuario(usuario);

        return bodega;
    }

    /* actualizar entidad */

    public void actualizarEntidadDesdeDTO(Bodega bodega, BodegaRequestDTO dto, Usuario usuario){

        if(dto == null || usuario == null) return;

        bodega.setNombre(dto.nombre());
        bodega.setUbicacion(dto.ubicacion());
        bodega.setCapacidad(dto.capacidad());
        bodega.setUsuario(usuario);

    }

}