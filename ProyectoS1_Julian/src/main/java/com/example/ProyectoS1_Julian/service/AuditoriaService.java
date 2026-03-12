// AuditoriaService.java
package com.example.ProyectoS1_Julian.service;

import com.example.ProyectoS1_Julian.dto.request.AuditoriaRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.AuditoriaResponseDTO;
import java.util.List;

public interface AuditoriaService {
    AuditoriaResponseDTO crearAuditoria(AuditoriaRequestDTO dto);
    AuditoriaResponseDTO actualizarAuditoria(AuditoriaRequestDTO dto, Long id);
    List<AuditoriaResponseDTO> listarAuditorias();
    AuditoriaResponseDTO buscarPorId(Long id);
    void eliminarAuditoria(Long id);
}