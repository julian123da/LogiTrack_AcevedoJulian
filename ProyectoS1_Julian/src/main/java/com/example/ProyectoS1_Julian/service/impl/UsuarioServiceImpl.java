package com.example.ProyectoS1_Julian.service.impl;

import com.example.ProyectoS1_Julian.dto.request.UsuarioRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.mapper.UsuarioMapper;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import com.example.ProyectoS1_Julian.repository.UsuarioRepository;
import com.example.ProyectoS1_Julian.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {

        Usuario usuario = usuarioMapper.toEntity(dto);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioGuardado);
    }

    @Override
    public List<UsuarioResponseDTO> listar() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizar(UsuarioRequestDTO dto, Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioMapper.actualizarEntidadDesdeDTO(usuario, dto);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioActualizado);
    }

    @Override
    public void eliminar(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);
    }

}