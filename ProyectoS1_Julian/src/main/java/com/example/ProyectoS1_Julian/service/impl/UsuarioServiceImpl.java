package com.example.ProyectoS1_Julian.service.impl;

import com.example.ProyectoS1_Julian.dto.request.UsuarioRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.mapper.UsuarioMapper;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import com.example.ProyectoS1_Julian.repository.BodegaRepository;
import com.example.ProyectoS1_Julian.repository.UsuarioRepository;
import com.example.ProyectoS1_Julian.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BodegaRepository bodegaRepository;

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        Usuario u = usuarioMapper.DTOAentidad(dto);
        Usuario uInsertado = usuarioRepository.save(u);
        return usuarioMapper.entidadADTO(uInsertado);
    }

    @Override
    public UsuarioResponseDTO actualizar(UsuarioRequestDTO dto, Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: no existe dicho Usuario a actualizar"));

        usuarioMapper.actualizarEntidadDesdeDTO(u, dto);
        Usuario uActualizado = usuarioRepository.save(u);
        return usuarioMapper.entidadADTO(uActualizado);
    }

    @Override
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::entidadADTO)
                .toList();
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: no existe dicho Usuario"));
        return usuarioMapper.entidadADTO(u);
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: no existe el Usuario a eliminar");
        }

        if (!bodegaRepository.existsByUsuarioId(id)){
            throw new EntityNotFoundException("Error, el usuario pertenece a una bodega");
        }
        usuarioRepository.deleteById(id);
    }
}
