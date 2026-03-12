package com.example.ProyectoS1_Julian.service;



import com.example.ProyectoS1_Julian.dto.request.DetalleMovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.DetalleMovimientoResponseDTO;

import java.util.List;

public interface DetalleMovimientoService {

    DetalleMovimientoResponseDTO crearDetalleMovimiento(DetalleMovimientoRequestDTO dto);

    DetalleMovimientoResponseDTO actualizarDetalleMovimiento(DetalleMovimientoRequestDTO dto, Long id);

    List<DetalleMovimientoResponseDTO> listarDetallesMovimiento();

    DetalleMovimientoResponseDTO buscarPorId(Long id);

    void eliminarDetalleMovimiento(Long id);

}