package com.example.ProyectoS1_Julian.service.impl;

import com.example.ProyectoS1_Julian.dto.request.DetalleMovimientoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.BodegaResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.DetalleMovimientoResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.MovimientoResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.ProductoResponseDTO;
import com.example.ProyectoS1_Julian.dto.response.UsuarioResponseDTO;
import com.example.ProyectoS1_Julian.mapper.BodegaMapper;
import com.example.ProyectoS1_Julian.mapper.DetalleMovimientoMapper;
import com.example.ProyectoS1_Julian.mapper.MovimientoMapper;
import com.example.ProyectoS1_Julian.mapper.ProductoMapper;
import com.example.ProyectoS1_Julian.mapper.UsuarioMapper;
import com.example.ProyectoS1_Julian.modelo.DetalleMovimiento;
import com.example.ProyectoS1_Julian.modelo.Movimiento;
import com.example.ProyectoS1_Julian.modelo.Producto;
import com.example.ProyectoS1_Julian.repository.DetalleMovimientoRepository;
import com.example.ProyectoS1_Julian.repository.MovimientoRepository;
import com.example.ProyectoS1_Julian.repository.ProductoRepository;
import com.example.ProyectoS1_Julian.service.DetalleMovimientoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DetalleMovimientoServiceImpl implements DetalleMovimientoService {

    private final DetalleMovimientoRepository detalleMovimientoRepository;
    private final DetalleMovimientoMapper detalleMovimientoMapper;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final BodegaMapper bodegaMapper;
    private final UsuarioMapper usuarioMapper;

    @Override
    public DetalleMovimientoResponseDTO crearDetalleMovimiento(DetalleMovimientoRequestDTO dto) {
        Movimiento movimiento = movimientoRepository.findById(dto.movimientoId())
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        Producto producto = productoRepository.findById(dto.productoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        DetalleMovimiento dm = detalleMovimientoMapper.DTOAentidad(dto, movimiento, producto);
        DetalleMovimiento dmGuardado = detalleMovimientoRepository.save(dm);

        return detalleMovimientoMapper.entidadADTO(
                dmGuardado,
                construirMovimientoDTO(dmGuardado.getMovimiento()),
                construirProductoDTO(dmGuardado.getProducto())
        );
    }

    @Override
    public DetalleMovimientoResponseDTO actualizarDetalleMovimiento(DetalleMovimientoRequestDTO dto, Long id) {
        DetalleMovimiento dm = detalleMovimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleMovimiento no encontrado"));

        Movimiento movimiento = movimientoRepository.findById(dto.movimientoId())
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado"));

        Producto producto = productoRepository.findById(dto.productoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

        detalleMovimientoMapper.actualizarEntidadDesdeDTO(dm, dto, movimiento, producto);
        DetalleMovimiento dmActualizado = detalleMovimientoRepository.save(dm);

        return detalleMovimientoMapper.entidadADTO(
                dmActualizado,
                construirMovimientoDTO(dmActualizado.getMovimiento()),
                construirProductoDTO(dmActualizado.getProducto())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleMovimientoResponseDTO> listarDetallesMovimiento() {
        return detalleMovimientoRepository.findAll().stream()
                .map(detalle -> detalleMovimientoMapper.entidadADTO(
                        detalle,
                        construirMovimientoDTO(detalle.getMovimiento()),
                        construirProductoDTO(detalle.getProducto())
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleMovimientoResponseDTO buscarPorId(Long id) {
        DetalleMovimiento dm = detalleMovimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DetalleMovimiento no encontrado"));

        return detalleMovimientoMapper.entidadADTO(
                dm,
                construirMovimientoDTO(dm.getMovimiento()),
                construirProductoDTO(dm.getProducto())
        );
    }

    @Override
    public void eliminarDetalleMovimiento(Long id) {
        if (!detalleMovimientoRepository.existsById(id)) {
            throw new EntityNotFoundException("DetalleMovimiento no encontrado para eliminar");
        }
        detalleMovimientoRepository.deleteById(id);
    }

    private MovimientoResponseDTO construirMovimientoDTO(Movimiento movimiento) {
        if (movimiento == null) {
            throw new EntityNotFoundException("El detalle no tiene movimiento asociado");
        }

        if (movimiento.getUsuario() == null) {
            throw new EntityNotFoundException("El movimiento no tiene usuario asociado");
        }

        if (movimiento.getBodegaOrigen() == null) {
            throw new EntityNotFoundException("El movimiento no tiene bodega origen asociada");
        }

        if (movimiento.getBodegaDestino() == null) {
            throw new EntityNotFoundException("El movimiento no tiene bodega destino asociada");
        }

        if (movimiento.getBodegaOrigen().getUsuario() == null) {
            throw new EntityNotFoundException("La bodega origen no tiene encargado asociado");
        }

        if (movimiento.getBodegaDestino().getUsuario() == null) {
            throw new EntityNotFoundException("La bodega destino no tiene encargado asociado");
        }

        UsuarioResponseDTO dtoUsuarioMovimiento = usuarioMapper.entidadADTO(movimiento.getUsuario());

        UsuarioResponseDTO dtoUsuarioOrigen = usuarioMapper.entidadADTO(movimiento.getBodegaOrigen().getUsuario());
        UsuarioResponseDTO dtoUsuarioDestino = usuarioMapper.entidadADTO(movimiento.getBodegaDestino().getUsuario());

        BodegaResponseDTO dtoOrigen = bodegaMapper.entidadADTO(movimiento.getBodegaOrigen(), dtoUsuarioOrigen);
        BodegaResponseDTO dtoDestino = bodegaMapper.entidadADTO(movimiento.getBodegaDestino(), dtoUsuarioDestino);

        return movimientoMapper.entidadADTO(movimiento, dtoUsuarioMovimiento, dtoOrigen, dtoDestino);
    }

    private ProductoResponseDTO construirProductoDTO(Producto producto) {
        if (producto == null) {
            throw new EntityNotFoundException("El detalle no tiene producto asociado");
        }

        if (producto.getBodega() == null) {
            throw new EntityNotFoundException("El producto no tiene bodega asociada");
        }

        if (producto.getBodega().getUsuario() == null) {
            throw new EntityNotFoundException("La bodega del producto no tiene encargado asociado");
        }

        UsuarioResponseDTO dtoUsuarioBodega = usuarioMapper.entidadADTO(producto.getBodega().getUsuario());
        BodegaResponseDTO dtoBodegaProducto = bodegaMapper.entidadADTO(producto.getBodega(), dtoUsuarioBodega);

        return productoMapper.entidadADTO(producto, dtoBodegaProducto);
    }
}