package com.example.ProyectoS1_Julian.service.impl;

import com.example.ProyectoS1_Julian.dto.request.MovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.BodegaResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.MovimientoResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.mapper.BodegaMapper;
import com.example.ProyectoS1_Julian.mapper.MovimientoMapper;
import com.example.ProyectoS1_Julian.mapper.UsuarioMapper;
import com.example.ProyectoS1_Julian.modelo.Bodega;
import com.example.ProyectoS1_Julian.modelo.Movimiento;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import com.example.ProyectoS1_Julian.repository.BodegaRepository;
import com.example.ProyectoS1_Julian.repository.MovimientoRepository;
import com.example.ProyectoS1_Julian.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements com.example.ProyectoS1_Julian.service.MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;

    @Override
    public MovimientoResponseDTO crearMovimiento(MovimientoRequestDTO dto) {
        // Obtener entidades relacionadas
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Bodega bodegaOrigen = bodegaRepository.findById(dto.idBodegaOrigen())
                .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada"));
        Bodega bodegaDestino = bodegaRepository.findById(dto.idBodegaDestino())
                .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada"));

        // Mapear DTO a entidad
        Movimiento movimiento = movimientoMapper.DTOAentidad(dto, usuario, bodegaOrigen, bodegaDestino);
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);

        // Mapear entidades relacionadas a DTOs
        UsuarioResponseDTO dtoUsuario = usuarioMapper.toDTO(usuario);
        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(bodegaOrigen, dtoUsuario);
        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(bodegaDestino, dtoUsuario);

        // Retornar DTO final
        return movimientoMapper.entidadADTO(movimientoGuardado, dtoUsuario, dtoOrigen, dtoDestino);
    }

    @Override
    public MovimientoResponseDTO actualizarMovimiento(MovimientoRequestDTO dto, Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Bodega bodegaOrigen = bodegaRepository.findById(dto.idBodegaOrigen())
                .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada"));
        Bodega bodegaDestino = bodegaRepository.findById(dto.idBodegaDestino())
                .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada"));

        movimientoMapper.actualizarEntidadDesdeDTO(movimiento, dto, usuario, bodegaOrigen, bodegaDestino);
        Movimiento movimientoActualizado = movimientoRepository.save(movimiento);

        UsuarioResponseDTO dtoUsuario = usuarioMapper.toDTO(usuario);
        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(bodegaOrigen, dtoUsuario);
        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(bodegaDestino, dtoUsuario);

        return movimientoMapper.entidadADTO(movimientoActualizado, dtoUsuario, dtoOrigen, dtoDestino);
    }

    @Override
    public List<MovimientoResponseDTO> listarMovimientos() {
        return movimientoRepository.findAll().stream().map(movimiento -> {
            UsuarioResponseDTO dtoUsuario = usuarioMapper.toDTO(movimiento.getUsuario());
            BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(movimiento.getBodegaOrigen(), dtoUsuario);
            BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(movimiento.getBodegaDestino(), dtoUsuario);
            return movimientoMapper.entidadADTO(movimiento, dtoUsuario, dtoOrigen, dtoDestino);
        }).toList();
    }

    @Override
    public MovimientoResponseDTO buscarPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        UsuarioResponseDTO dtoUsuario = usuarioMapper.toDTO(movimiento.getUsuario());
        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(movimiento.getBodegaOrigen(), dtoUsuario);
        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(movimiento.getBodegaDestino(), dtoUsuario);

        return movimientoMapper.entidadADTO(movimiento, dtoUsuario, dtoOrigen, dtoDestino);
    }

    @Override
    public void eliminarMovimiento(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new EntityNotFoundException("Movimiento no encontrado");
        }
        movimientoRepository.deleteById(id);
    }
}