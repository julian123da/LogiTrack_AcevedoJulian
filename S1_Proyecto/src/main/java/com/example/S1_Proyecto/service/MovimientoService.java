package com.example.S1_Proyecto.service;



import com.example.S1_Proyecto.dto.request.MovimientoRequestDTO;
import com.example.S1_Proyecto.dto.response.MovimientoResponseDTO;

import java.util.List;

public interface MovimientoService {

    MovimientoResponseDTO crearMovimiento(MovimientoRequestDTO dto);

    MovimientoResponseDTO actualizarMovimiento(MovimientoRequestDTO dto, Long id);

    List<MovimientoResponseDTO> listarMovimientos();

    MovimientoResponseDTO buscarPorId(Long id);

    void eliminarMovimiento(Long id);

    List<MovimientoResponseDTO> Recientes();

}