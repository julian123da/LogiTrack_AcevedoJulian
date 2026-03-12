package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.MovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.MovimientoResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {

    public Movimiento toEntity(MovimientoRequestDTO dto){

        if (dto == null) return null;

        Movimiento movimiento = new Movimiento();

        movimiento.setFecha(dto.fecha());
        movimiento.setTipoMovimiento(dto.tipoMovimiento());

        return movimiento;
    }

    public MovimientoResponseDTO toDTO(Movimiento movimiento){

        if (movimiento == null) return null;

        return new MovimientoResponseDTO(
                movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                null,
                null,
                null
        );
    }
}