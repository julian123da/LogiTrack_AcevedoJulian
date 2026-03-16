package com.example.S1_Proyecto.service.impl;


import com.example.S1_Proyecto.dto.request.AuditoriaRequestDTO;
import com.example.S1_Proyecto.dto.response.AuditoriaResponseDTO;
import com.example.S1_Proyecto.dto.response.UsuarioResponseDTO;
import com.example.S1_Proyecto.mapper.AuditoriaMapper;
import com.example.S1_Proyecto.mapper.UsuarioMapper;
import com.example.S1_Proyecto.modelo.Auditoria;
import com.example.S1_Proyecto.modelo.Usuario;
import com.example.S1_Proyecto.repository.AuditoriaRepository;
import com.example.S1_Proyecto.repository.UsuarioRepository;
import com.example.S1_Proyecto.service.AuditoriaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final AuditoriaMapper auditoriaMapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public AuditoriaResponseDTO crearAuditoria(AuditoriaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Error: no existe el usuario"));

        Auditoria a = auditoriaMapper.DTOAentidad(dto, usuario);
        Auditoria aInsertada = auditoriaRepository.save(a);

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(usuario);
        return auditoriaMapper.entidadADTO(aInsertada, dtoUsuario);
    }

    @Override
    public AuditoriaResponseDTO actualizarAuditoria(AuditoriaRequestDTO dto, Long id) {
        Auditoria a = auditoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: no existe dicha Auditoria a actualizar"));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Error: no existe el usuario"));

        auditoriaMapper.actualizarEntidadDesdeDTO(a, dto, usuario);
        Auditoria aActualizada = auditoriaRepository.save(a);

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(usuario);
        return auditoriaMapper.entidadADTO(aActualizada, dtoUsuario);
    }

    @Override
    public List<AuditoriaResponseDTO> listarAuditorias() {
        return auditoriaRepository.findAll().stream().map(dato -> {
            UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(
                    usuarioRepository.findById(dato.getUsuario().getId())
                            .orElseThrow(() -> new RuntimeException("Error: no existe el usuario")));
            return auditoriaMapper.entidadADTO(dato, dtoUsuario);
        }).toList();
    }

    @Override
    public AuditoriaResponseDTO buscarPorId(Long id) {
        Auditoria a = auditoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: no existe dicha Auditoria"));

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(
                usuarioRepository.findById(a.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Error: no existe el usuario")));

        return auditoriaMapper.entidadADTO(a, dtoUsuario);
    }

    @Override
    public void eliminarAuditoria(Long id) {
        if (!auditoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: no existe la Auditoria a eliminar");
        }
        auditoriaRepository.deleteById(id);
    }
}