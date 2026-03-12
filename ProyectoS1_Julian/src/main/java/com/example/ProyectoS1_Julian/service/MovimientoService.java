package com.example.ProyectoS1_Julian.service;

import com.example.ProyectoS1_Julian.dto.request.MovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.MovimientoResponseDTO;

import java.util.List;

public interface MovimientoService {

    MovimientoResponseDTO crearMovimiento(MovimientoRequestDTO dto);

    MovimientoResponseDTO actualizarMovimiento(MovimientoRequestDTO dto, Long id);

    List<MovimientoResponseDTO> listarMovimientos();

    MovimientoResponseDTO buscarPorId(Long id);

    void eliminarMovimiento(Long id);

}