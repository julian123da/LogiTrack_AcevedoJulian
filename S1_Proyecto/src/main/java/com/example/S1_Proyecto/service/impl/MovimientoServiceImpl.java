package com.example.S1_Proyecto.service.impl;

import com.example.S1_Proyecto.dto.request.MovimientoRequestDTO;
import com.example.S1_Proyecto.dto.response.BodegaResponseDTO;
import com.example.S1_Proyecto.dto.response.MovimientoResponseDTO;
import com.example.S1_Proyecto.dto.response.UsuarioResponseDTO;
import com.example.S1_Proyecto.mapper.BodegaMapper;
import com.example.S1_Proyecto.mapper.MovimientoMapper;
import com.example.S1_Proyecto.mapper.UsuarioMapper;
import com.example.S1_Proyecto.modelo.Bodega;
import com.example.S1_Proyecto.modelo.Movimiento;
import com.example.S1_Proyecto.modelo.Usuario;
import com.example.S1_Proyecto.repository.BodegaRepository;
import com.example.S1_Proyecto.repository.MovimientoRepository;
import com.example.S1_Proyecto.repository.UsuarioRepository;
import com.example.S1_Proyecto.service.MovimientoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;
    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;

    @Override
    public MovimientoResponseDTO crearMovimiento(MovimientoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Bodega bodegaOrigen = bodegaRepository.findById(dto.bodegaOrigenId())
                .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada"));
        Bodega bodegaDestino = bodegaRepository.findById(dto.bodegaDestinoId())
                .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada"));

        Movimiento movimiento = movimientoMapper.DTOAentidad(dto, usuario, bodegaOrigen, bodegaDestino);
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(usuario);
        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(bodegaOrigen, dtoUsuario);
        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(bodegaDestino, dtoUsuario);

        return movimientoMapper.entidadADTO(movimientoGuardado, dtoUsuario, dtoOrigen, dtoDestino);
    }

    @Override
    public MovimientoResponseDTO actualizarMovimiento(MovimientoRequestDTO dto, Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Bodega bodegaOrigen = bodegaRepository.findById(dto.bodegaOrigenId())
                .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada"));
        Bodega bodegaDestino = bodegaRepository.findById(dto.bodegaDestinoId())
                .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada"));

        movimientoMapper.actualizarEntidadDesdeDTO(movimiento, dto, usuario, bodegaOrigen, bodegaDestino);
        Movimiento movimientoActualizado = movimientoRepository.save(movimiento);

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(usuario);
        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(bodegaOrigen, dtoUsuario);
        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(bodegaDestino, dtoUsuario);

        return movimientoMapper.entidadADTO(movimientoActualizado, dtoUsuario, dtoOrigen, dtoDestino);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoResponseDTO> listarMovimientos() {
        return movimientoRepository.findAll().stream().map(movimiento -> {
            UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(movimiento.getUsuario());
            BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(movimiento.getBodegaOrigen(), dtoUsuario);
            BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(movimiento.getBodegaDestino(), dtoUsuario);
            return movimientoMapper.entidadADTO(movimiento, dtoUsuario, dtoOrigen, dtoDestino);
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoResponseDTO buscarPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(movimiento.getUsuario());
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