package com.example.ProyectoS1_Julian.mapper;

import com.example.ProyectoS1_Julian.dto.request.InventarioRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.InventarioResponseDTO;
import com.example.ProyectoS1_Julian.modelo.Inventario;
import org.springframework.stereotype.Component;

@Component
public class InventarioMapper {

    public Inventario toEntity(InventarioRequestDTO dto){

        if (dto == null) return null;

        Inventario inventario = new Inventario();

        inventario.setCantidad(dto.cantidad());

        return inventario;
    }

    public InventarioResponseDTO toDTO(Inventario inventario){

        if (inventario == null) return null;

        return new InventarioResponseDTO(
                inventario.getId(),
                null,
                null,
                inventario.getCantidad()
        );
    }
};