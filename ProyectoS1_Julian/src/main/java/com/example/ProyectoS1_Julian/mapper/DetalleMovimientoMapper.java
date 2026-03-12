package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.DetalleMovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.DetalleMovimientoResponseDTO;
import com.example.ProyectoS1_Julian.modelo.DetalleMovimiento;
import org.springframework.stereotype.Component;

@Component
public class DetalleMovimientoMapper {

    public DetalleMovimiento toEntity(DetalleMovimientoRequestDTO dto){

        if(dto == null) return null;

        DetalleMovimiento detalle = new DetalleMovimiento();

        detalle.setCantidad(dto.cantidad());

        return detalle;
    }

    public DetalleMovimientoResponseDTO toDTO(DetalleMovimiento detalle){

        if(detalle == null) return null;

        return new DetalleMovimientoResponseDTO(
                detalle.getId(),
                null,
                null,
                detalle.getCantidad()
        );
    }

}