package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.ProductoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.ProductoResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    /* entidad → DTO */

    public ProductoResponseDTO entidadADTO(Producto producto){

        if(producto == null) return null;

        return new ProductoResponseDTO(

                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getStockTotal()

        );
    }

    /* DTO → entidad */

    public Producto DTOAentidad(ProductoRequestDTO dto){

        if(dto == null) return null;

        Producto producto = new Producto();

        producto.setNombre(dto.nombre());
        producto.setCategoria(dto.categoria());

        // según tu modelo estos métodos no reciben parámetros
        producto.setPrecio();
        producto.setStockTotal();

        return producto;
    }

    /* actualizar entidad */

    public void actualizarEntidadDesdeDTO(Producto producto,
                                          ProductoRequestDTO dto){

        if(dto == null || producto == null) return;

        producto.setNombre(dto.nombre());
        producto.setCategoria(dto.categoria());

        producto.setPrecio();
        producto.setStockTotal();

    }

}