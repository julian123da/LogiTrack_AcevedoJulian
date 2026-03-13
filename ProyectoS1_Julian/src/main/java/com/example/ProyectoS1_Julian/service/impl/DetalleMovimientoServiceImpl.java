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
    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public DetalleMovimientoResponseDTO crearDetalleMovimiento(DetalleMovimientoRequestDTO dto) {
        Movimiento movimiento = movimientoRepository.findById(dto.movimientoId())
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        Producto producto = productoRepository.findById(dto.productoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetalleMovimiento dm = detalleMovimientoMapper.DTOAentidad(dto, movimiento, producto);
        DetalleMovimiento dmGuardado = detalleMovimientoRepository.save(dm);

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(
                usuarioRepository.findById(movimiento.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(
                bodegaRepository.findById(movimiento.getBodegaOrigen().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada")), dtoUsuario);

        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(
                bodegaRepository.findById(movimiento.getBodegaDestino().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada")), dtoUsuario);

        MovimientoResponseDTO dtoMovimiento = movimientoMapper.entidadADTO(movimiento, dtoUsuario, dtoOrigen, dtoDestino);

        UsuarioResponseDTO dtoUsuarioBodega = usuarioMapper.entidadADTO(
                usuarioRepository.findById(producto.getBodega().getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario de la bodega no encontrado")));

        BodegaResponseDTO dtoBodegaProducto = bodegaMapper.entidadADTO(
                bodegaRepository.findById(producto.getBodega().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega del producto no encontrada")), dtoUsuarioBodega);

        ProductoResponseDTO dtoProducto = productoMapper.entidadADTO(producto, dtoBodegaProducto);

        return detalleMovimientoMapper.entidadADTO(dmGuardado, dtoMovimiento, dtoProducto);
    }

    @Override
    public DetalleMovimientoResponseDTO actualizarDetalleMovimiento(DetalleMovimientoRequestDTO dto, Long id) {
        DetalleMovimiento dm = detalleMovimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleMovimiento no encontrado"));

        Movimiento movimiento = movimientoRepository.findById(dto.movimientoId())
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        Producto producto = productoRepository.findById(dto.productoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        detalleMovimientoMapper.actualizarEntidadDesdeDTO(dm, dto, movimiento, producto);

        DetalleMovimiento dmActualizado = detalleMovimientoRepository.save(dm);

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(
                usuarioRepository.findById(movimiento.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(
                bodegaRepository.findById(movimiento.getBodegaOrigen().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada")), dtoUsuario);

        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(
                bodegaRepository.findById(movimiento.getBodegaDestino().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada")), dtoUsuario);

        MovimientoResponseDTO dtoMovimiento = movimientoMapper.entidadADTO(dmActualizado.getMovimiento(), dtoUsuario, dtoOrigen, dtoDestino);

        UsuarioResponseDTO dtoUsuarioBodega = usuarioMapper.entidadADTO(
                usuarioRepository.findById(producto.getBodega().getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario de la bodega no encontrado")));

        BodegaResponseDTO dtoBodegaProducto = bodegaMapper.entidadADTO(
                bodegaRepository.findById(producto.getBodega().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega del producto no encontrada")), dtoUsuarioBodega);

        ProductoResponseDTO dtoProducto = productoMapper.entidadADTO(producto, dtoBodegaProducto);

        return detalleMovimientoMapper.entidadADTO(dmActualizado, dtoMovimiento, dtoProducto);
    }

    @Override
    public List<DetalleMovimientoResponseDTO> listarDetallesMovimiento() {
        return detalleMovimientoRepository.findAll().stream().map(dato -> {
            Movimiento movimiento = movimientoRepository.findById(dato.getMovimiento().getId())
                    .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

            Producto producto = productoRepository.findById(dato.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(
                    usuarioRepository.findById(movimiento.getUsuario().getId())
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

            BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(
                    bodegaRepository.findById(movimiento.getBodegaOrigen().getId())
                            .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada")), dtoUsuario);

            BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(
                    bodegaRepository.findById(movimiento.getBodegaDestino().getId())
                            .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada")), dtoUsuario);

            MovimientoResponseDTO dtoMovimiento = movimientoMapper.entidadADTO(movimiento, dtoUsuario, dtoOrigen, dtoDestino);

            UsuarioResponseDTO dtoUsuarioBodega = usuarioMapper.entidadADTO(
                    usuarioRepository.findById(producto.getBodega().getUsuario().getId())
                            .orElseThrow(() -> new RuntimeException("Usuario de la bodega no encontrado")));

            BodegaResponseDTO dtoBodegaProducto = bodegaMapper.entidadADTO(
                    bodegaRepository.findById(producto.getBodega().getId())
                            .orElseThrow(() -> new RuntimeException("Bodega del producto no encontrada")), dtoUsuarioBodega);

            ProductoResponseDTO dtoProducto = productoMapper.entidadADTO(producto, dtoBodegaProducto);

            return detalleMovimientoMapper.entidadADTO(dato, dtoMovimiento, dtoProducto);
        }).toList();
    }

    @Override
    public DetalleMovimientoResponseDTO buscarPorId(Long id) {
        DetalleMovimiento dm = detalleMovimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleMovimiento no encontrado"));

        Movimiento movimiento = movimientoRepository.findById(dm.getMovimiento().getId())
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        Producto producto = productoRepository.findById(dm.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(
                usuarioRepository.findById(movimiento.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(
                bodegaRepository.findById(movimiento.getBodegaOrigen().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega origen no encontrada")), dtoUsuario);

        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(
                bodegaRepository.findById(movimiento.getBodegaDestino().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega destino no encontrada")), dtoUsuario);

        MovimientoResponseDTO dtoMovimiento = movimientoMapper.entidadADTO(movimiento, dtoUsuario, dtoOrigen, dtoDestino);

        UsuarioResponseDTO dtoUsuarioBodega = usuarioMapper.entidadADTO(
                usuarioRepository.findById(producto.getBodega().getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Usuario de la bodega no encontrado")));

        BodegaResponseDTO dtoBodegaProducto = bodegaMapper.entidadADTO(
                bodegaRepository.findById(producto.getBodega().getId())
                        .orElseThrow(() -> new RuntimeException("Bodega del producto no encontrada")), dtoUsuarioBodega);

        ProductoResponseDTO dtoProducto = productoMapper.entidadADTO(producto, dtoBodegaProducto);

        return detalleMovimientoMapper.entidadADTO(dm, dtoMovimiento, dtoProducto);
    }

    @Override
    public void eliminarDetalleMovimiento(Long id) {
        if (!detalleMovimientoRepository.existsById(id)) {
            throw new EntityNotFoundException("DetalleMovimiento no encontrado para eliminar");
        }
        detalleMovimientoRepository.deleteById(id);
    }
}