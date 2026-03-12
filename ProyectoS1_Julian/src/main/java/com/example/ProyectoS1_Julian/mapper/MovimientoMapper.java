package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.BodegaRequestDTO;
import com.example.ProyectoS1_Julian.dto.request.MovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.BodegaResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.MovimientoResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Bodega;
import com.example.ProyectoS1_Julian.modelo.Movimiento;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {



    public MovimientoResponseDTO entidadADTO(Movimiento movimiento,
                                             UsuarioResponseDTO dtoU,
                                             BodegaResponseDTO bodegaOrigen,
                                             BodegaResponseDTO bodegaDestino){

        if (movimiento == null) return null;

        return new MovimientoResponseDTO(
                movimiento.getId(),
                movimiento.getFecha(),
                movimiento.getTipoMovimiento(),
                dtoU,
                bodegaOrigen,
                bodegaDestino
        );
    }

    public Movimiento DTOAentidad(
            MovimientoRequestDTO dto, Usuario usuario, Bodega bodegaOrigen, Bodega bodegaDestino
    ){
        if (dto==null || usuario==null|| bodegaOrigen== null||bodegaDestino==null) return null;
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(dto.fecha());
        movimiento.setTipoMovimiento(dto.tipoMovimiento());
        movimiento.setUsuario(usuario);
        movimiento.setBodegaOrigen(bodegaOrigen);
        movimiento.setBodegaDestino(bodegaDestino);
        return movimiento;
    }

    public void actualizarEntidadDesdeDTO (Movimiento movimiento, MovimientoRequestDTO dto, Usuario usuario, Bodega bodegaOrigen,
                                           Bodega bodegaDestino) {
        if(dto == null || usuario == null || bodegaDestino==null || bodegaOrigen== null) return;
        movimiento.setFecha(dto.fecha());
        movimiento.setTipoMovimiento(dto.tipoMovimiento());
        movimiento.setUsuario(usuario);
        movimiento.setBodegaOrigen(bodegaOrigen);
        movimiento.setBodegaDestino(bodegaDestino);
    }


}