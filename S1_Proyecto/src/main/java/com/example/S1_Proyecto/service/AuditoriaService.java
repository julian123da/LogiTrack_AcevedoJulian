// AuditoriaService.java
package com.example.S1_Proyecto.service;



import com.example.S1_Proyecto.dto.request.AuditoriaRequestDTO;
import com.example.S1_Proyecto.dto.response.AuditoriaResponseDTO;

import java.util.List;

public interface AuditoriaService {
    AuditoriaResponseDTO crearAuditoria(AuditoriaRequestDTO dto);
    AuditoriaResponseDTO actualizarAuditoria(AuditoriaRequestDTO dto, Long id);
    List<AuditoriaResponseDTO> listarAuditorias();
    AuditoriaResponseDTO buscarPorId(Long id);
    void eliminarAuditoria(Long id);
}