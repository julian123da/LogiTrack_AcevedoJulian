package com.example.S1_Proyecto.service;



import com.example.S1_Proyecto.dto.request.UsuarioRequestDTO;
import com.example.S1_Proyecto.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO crear(UsuarioRequestDTO dto);

    List<UsuarioResponseDTO> listar();

    UsuarioResponseDTO buscarPorId(Long id);

    UsuarioResponseDTO actualizar(UsuarioRequestDTO dto, Long id);

    void eliminar(Long id);

}