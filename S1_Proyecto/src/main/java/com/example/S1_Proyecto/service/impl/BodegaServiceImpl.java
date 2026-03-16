package com.example.S1_Proyecto.service.impl;


import com.example.S1_Proyecto.dto.request.BodegaRequestDTO;
import com.example.S1_Proyecto.dto.response.BodegaResponseDTO;
import com.example.S1_Proyecto.dto.response.UsuarioResponseDTO;
import com.example.S1_Proyecto.mapper.BodegaMapper;
import com.example.S1_Proyecto.mapper.UsuarioMapper;
import com.example.S1_Proyecto.modelo.Bodega;
import com.example.S1_Proyecto.modelo.Usuario;
import com.example.S1_Proyecto.repository.BodegaRepository;
import com.example.S1_Proyecto.repository.UsuarioRepository;
import com.example.S1_Proyecto.service.BodegaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BodegaServiceImpl implements BodegaService {

    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;

    @Override
    public BodegaResponseDTO crearBodega(BodegaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Bodega bodega = bodegaMapper.DTOAentidad(dto, usuario);
        bodega = bodegaRepository.save(bodega);

        return bodegaMapper.entidadADTO(bodega, usuarioMapper.entidadADTO(usuario));
    }

    @Override
    public BodegaResponseDTO actualizarBodega(BodegaRequestDTO dto, Long id) {
        Bodega bodega = bodegaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bodega no encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        bodegaMapper.actualizarEntidadDesdeDTO(bodega, dto, usuario);
        bodega = bodegaRepository.save(bodega);

        return bodegaMapper.entidadADTO(bodega, usuarioMapper.entidadADTO(usuario));
    }

    @Override
    public List<BodegaResponseDTO> listarBodegas() {
        return bodegaRepository.findAll().stream().map(dato-> bodegaMapper.entidadADTO(dato,usuarioMapper
                .entidadADTO(usuarioRepository.findById(dato.getUsuario().getId())
                        .orElseThrow(()->new RuntimeException("No existe la bodega"))))).toList();
    }

    @Override
    public BodegaResponseDTO buscarPorId(Long id) {
        Bodega bodega = bodegaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bodega no encontrada"));

        Usuario u = usuarioRepository.findById(bodega.getUsuario().getId()).orElseThrow(()-> new RuntimeException("Error"));
        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(u);
        return bodegaMapper.entidadADTO(bodega,dtoUsuario);
    }

    @Override
    public void eliminarBodega(Long id) {
        if (!bodegaRepository.existsById(id)) {
            throw new EntityNotFoundException("Bodega no existe, no se puede eliminar");
        }
        bodegaRepository.deleteById(id);
    }
}