package com.example.S1_Proyecto.service.impl;


import com.example.S1_Proyecto.dto.request.UsuarioRequestDTO;
import com.example.S1_Proyecto.dto.response.UsuarioResponseDTO;
import com.example.S1_Proyecto.exception.BusinessRuleException;
import com.example.S1_Proyecto.mapper.UsuarioMapper;
import com.example.S1_Proyecto.modelo.Usuario;
import com.example.S1_Proyecto.repository.BodegaRepository;
import com.example.S1_Proyecto.repository.UsuarioRepository;
import com.example.S1_Proyecto.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BodegaRepository bodegaRepository;
    private final PasswordEncoder passwordEncoder;  // inyectado desde SecurityConfig

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        // Validar que el username no exista ya
        if (usuarioRepository.findByUsername(dto.username()).isPresent()) {
            throw new BusinessRuleException("El username '" + dto.username() + "' ya está en uso");
        }

        Usuario u = usuarioMapper.DTOAentidad(dto);

        // Encriptar la contraseña antes de guardar en BD
        u.setPassword(passwordEncoder.encode(dto.password()));

        Usuario uInsertado = usuarioRepository.save(u);
        return usuarioMapper.entidadADTO(uInsertado);
    }

    @Override
    public UsuarioResponseDTO actualizar(UsuarioRequestDTO dto, Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario con id: " + id));

        usuarioMapper.actualizarEntidadDesdeDTO(u, dto);

        // Encriptar la nueva contraseña antes de actualizar
        u.setPassword(passwordEncoder.encode(dto.password()));

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
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario con id: " + id));
        return usuarioMapper.entidadADTO(u);
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe el usuario con id: " + id);
        }
        if (bodegaRepository.existsByUsuarioId(id)) {
            throw new BusinessRuleException("No se puede eliminar: el usuario está asignado a una bodega");
        }
        usuarioRepository.deleteById(id);
    }
}