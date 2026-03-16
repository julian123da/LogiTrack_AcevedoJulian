package com.example.S1_Proyecto.service;



import com.example.S1_Proyecto.dto.request.BodegaRequestDTO;
import com.example.S1_Proyecto.dto.response.BodegaResponseDTO;

import java.util.List;

public interface BodegaService {

    BodegaResponseDTO crearBodega(BodegaRequestDTO dto);
    BodegaResponseDTO actualizarBodega(BodegaRequestDTO dto, Long id);
    List<BodegaResponseDTO> listarBodegas();
    BodegaResponseDTO buscarPorId(Long id);
    void eliminarBodega(Long id);
}