package com.example.S1_Proyecto.service;





import com.example.S1_Proyecto.dto.request.DetalleMovimientoRequestDTO;
import com.example.S1_Proyecto.dto.response.DetalleMovimientoResponseDTO;

import java.util.List;

public interface DetalleMovimientoService {

    DetalleMovimientoResponseDTO crearDetalleMovimiento(DetalleMovimientoRequestDTO dto);

    DetalleMovimientoResponseDTO actualizarDetalleMovimiento(DetalleMovimientoRequestDTO dto, Long id);

    List<DetalleMovimientoResponseDTO> listarDetallesMovimiento();

    DetalleMovimientoResponseDTO buscarPorId(Long id);

    void eliminarDetalleMovimiento(Long id);

}