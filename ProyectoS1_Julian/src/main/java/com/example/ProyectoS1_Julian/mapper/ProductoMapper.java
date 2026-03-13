package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.BodegaRequestDTO;
import com.example.ProyectoS1_Julian.dto.request.ProductoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.BodegaResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.ProductoResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Bodega;
import com.example.ProyectoS1_Julian.modelo.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    /* entidad → DTO */

    public ProductoResponseDTO entidadADTO(Producto producto, BodegaResponseDTO dto){
        if(producto == null ) return null;

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getStock(),
                dto
        );
    }


    /* DTO → entidad */

    public Producto DTOAentidad (ProductoRequestDTO dto, Bodega bodega){
        if (dto== null || bodega==null) return null;

        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setCategoria(dto.categoria());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setBodega(bodega);

        return producto;


    }


    /* actualizar entidad */

    public void actualizarEntidadDesdeDTO(Producto producto,
                                          ProductoRequestDTO dto, Bodega bodega){

        if(dto == null || producto == null) return;

        producto.setNombre(dto.nombre());
        producto.setCategoria(dto.categoria());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setBodega(bodega);

    }

}