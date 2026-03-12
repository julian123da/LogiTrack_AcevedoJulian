package com.example.ProyectoS1_Julian.service;

import com.example.ProyectoS1_Julian.dto.request.BodegaRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.BodegaResponseDTO;

import java.util.List;

public interface BodegaService {

    BodegaResponseDTO crearBodega(BodegaRequestDTO dto);
    BodegaResponseDTO actualizarBodega(BodegaRequestDTO dto, Long id);
    List<BodegaResponseDTO> listarBodegas();
    BodegaResponseDTO buscarPorId(Long id);
    void eliminarBodega(Long id);
}