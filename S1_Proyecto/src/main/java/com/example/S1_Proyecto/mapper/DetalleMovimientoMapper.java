package com.example.S1_Proyecto.mapper;


import com.example.S1_Proyecto.dto.request.DetalleMovimientoRequestDTO;
import com.example.S1_Proyecto.dto.response.DetalleMovimientoResponseDTO;
import com.example.S1_Proyecto.dto.response.MovimientoResponseDTO;
import com.example.S1_Proyecto.dto.response.ProductoResponseDTO;
import com.example.S1_Proyecto.modelo.DetalleMovimiento;
import com.example.S1_Proyecto.modelo.Movimiento;
import com.example.S1_Proyecto.modelo.Producto;
import org.springframework.stereotype.Component;

@Component
public class DetalleMovimientoMapper {

    /* entidad → DTO */

    public DetalleMovimientoResponseDTO entidadADTO(DetalleMovimiento detalle, MovimientoResponseDTO dto,
                                                    ProductoResponseDTO dtop){

        if(detalle == null) return null;

        return new DetalleMovimientoResponseDTO(

                detalle.getId(),
                detalle.getCantidad(),
                dto,
                dtop
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