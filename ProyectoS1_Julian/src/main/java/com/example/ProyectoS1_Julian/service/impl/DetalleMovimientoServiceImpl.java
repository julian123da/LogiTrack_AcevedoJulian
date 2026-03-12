package com.example.ProyectoS1_Julian.service.impl;

import com.example.ProyectoS1_Julian.service.DetalleMovimientoService;
import com.example.ProyectoS1_Julian.dto.request.DetalleMovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.*;
import com.example.ProyectoS1_Julian.mapper.*;
import com.example.ProyectoS1_Julian.modelo.Movimiento;
import com.example.ProyectoS1_Julian.modelo.DetalleMovimiento;
import com.example.ProyectoS1_Julian.modelo.Producto;
import com.example.ProyectoS1_Julian.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleMovimientoServiceImpl implements DetalleMovimientoService {

    private final DetalleMovimientoRepository detalleMovimientoRepository;
    private final DetalleMovimientoMapper detalleMovimientoMapper;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public DetalleMovimientoResponseDTO crearDetalleMovimiento(DetalleMovimientoRequestDTO dto) {
        Movimiento movimiento = movimientoRepository.findById(dto.idMovimiento())
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        Producto producto = productoRepository.findById(dto.idProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetalleMovimiento dm = detalleMovimientoMapper.DTOAentidad(dto, movimiento, producto);
        DetalleMovimiento dmGuardado = detalleMovimientoRepository.save(dm);

        return detalleMovimientoMapper.entidadADTO(dmGuardado);
    }

    @Override
    public DetalleMovimientoResponseDTO actualizarDetalleMovimiento(DetalleMovimientoRequestDTO dto, Long id) {
        DetalleMovimiento dm = detalleMovimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleMovimiento no encontrado"));

        Movimiento movimiento = movimientoRepository.findById(dto.idMovimiento())
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        Producto producto = productoRepository.findById(dto.idProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        detalleMovimientoMapper.actualizarEntidadDesdeDTO(dm, dto, movimiento, producto);

        DetalleMovimiento dmActualizado = detalleMovimientoRepository.save(dm);

        return detalleMovimientoMapper.entidadADTO(dmActualizado);
    }

    @Override
    public List<DetalleMovimientoResponseDTO> listarDetallesMovimiento() {
        return detalleMovimientoRepository.findAll()
                .stream()
                .map(detalleMovimientoMapper::entidadADTO)
                .toList();
    }

    @Override
    public DetalleMovimientoResponseDTO buscarPorId(Long id) {
        DetalleMovimiento dm = detalleMovimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleMovimiento no encontrado"));

        return detalleMovimientoMapper.entidadADTO(dm);
    }

    @Override
    public void eliminarDetalleMovimiento(Long id) {
        if (!detalleMovimientoRepository.existsById(id)) {
            throw new EntityNotFoundException("DetalleMovimiento no encontrado para eliminar");
        }
        detalleMovimientoRepository.deleteById(id);
    }
}