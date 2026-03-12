package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.DetalleMovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.DetalleMovimientoResponseDTO;
import com.example.ProyectoS1_Julian.modelo.DetalleMovimiento;
import com.example.ProyectoS1_Julian.modelo.Movimiento;
import com.example.ProyectoS1_Julian.modelo.Producto;
import org.springframework.stereotype.Component;

@Component
public class DetalleMovimientoMapper {

    /* entidad → DTO */

    public DetalleMovimientoResponseDTO entidadADTO(DetalleMovimiento detalle){

        if(detalle == null) return null;

        return new DetalleMovimientoResponseDTO(

                detalle.getId(),
                detalle.getMovimiento().getId(),
                detalle.getProducto().getId(),
                detalle.getCantidad()

        );
    }

    /* DTO → entidad */

    public DetalleMovimiento DTOAentidad(DetalleMovimientoRequestDTO dto,
                                         Movimiento movimiento,
                                         Producto producto){

        if(dto == null || movimiento == null || producto == null) return null;

        DetalleMovimiento detalle = new DetalleMovimiento();

        detalle.setCantidad(dto.cantidad());
        detalle.setMovimiento(movimiento);
        detalle.setProducto(producto);

        return detalle;
    }

    /* actualizar entidad */

    public void actualizarEntidadDesdeDTO(DetalleMovimiento detalle,
                                          DetalleMovimientoRequestDTO dto,
                                          Movimiento movimiento,
                                          Producto producto){

        if(dto == null || movimiento == null || producto == null) return;

        detalle.setCantidad(dto.cantidad());
        detalle.setMovimiento(movimiento);
        detalle.setProducto(producto);

    }

}