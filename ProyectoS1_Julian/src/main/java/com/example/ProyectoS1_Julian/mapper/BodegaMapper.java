package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.BodegaRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.BodegaResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Bodega;
import org.springframework.stereotype.Component;

@Component
public class BodegaMapper {

    public Bodega toEntity(BodegaRequestDTO dto){
        if (dto == null) return null;

        Bodega bodega = new Bodega();

        bodega.setNombre(dto.nombre());
        bodega.setUbicacion(dto.ubicacion());
        bodega.setCapacidad(dto.capacidad());

        return bodega;
    }

    public BodegaResponseDTO toDTO(Bodega bodega){

        if (bodega == null) return null;

        return new BodegaResponseDTO(
                bodega.getId(),
                bodega.getNombre(),
                bodega.getUbicacion(),
                bodega.getCapacidad(),
                null
        );
    }
}