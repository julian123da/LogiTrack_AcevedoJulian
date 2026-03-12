package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.InventarioRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.InventarioResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Bodega;
import com.example.ProyectoS1_Julian.modelo.Inventario;
import com.example.ProyectoS1_Julian.modelo.Producto;
import org.springframework.stereotype.Component;

@Component
public class InventarioMapper {

    /* entidad → DTO */

    public InventarioResponseDTO entidadADTO(Inventario inventario){

        if(inventario == null) return null;

        return new InventarioResponseDTO(

                inventario.getId(),
                inventario.getBodega().getId(),
                inventario.getProducto().getId(),
                inventario.getCantidad()

        );
    }

    /* DTO → entidad */

    public Inventario DTOAentidad(InventarioRequestDTO dto,
                                  Bodega bodega,
                                  Producto producto){

        if(dto == null || bodega == null || producto == null) return null;

        Inventario inventario = new Inventario();

        inventario.setCantidad(dto.cantidad());
        inventario.setBodega(bodega);
        inventario.setProducto(producto);

        return inventario;
    }

    /* actualizar entidad */

    public void actualizarEntidadDesdeDTO(Inventario inventario,
                                          InventarioRequestDTO dto,
                                          Bodega bodega,
                                          Producto producto){

        if(dto == null || bodega == null || producto == null) return;

        inventario.setCantidad(dto.cantidad());
        inventario.setBodega(bodega);
        inventario.setProducto(producto);

    }

}