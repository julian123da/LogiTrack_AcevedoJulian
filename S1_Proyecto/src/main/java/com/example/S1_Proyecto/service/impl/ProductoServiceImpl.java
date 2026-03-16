package com.example.S1_Proyecto.service.impl;

import com.example.S1_Proyecto.dto.request.ProductoRequestDTO;
import com.example.S1_Proyecto.dto.response.BodegaResponseDTO;
import com.example.S1_Proyecto.dto.response.ProductoResponseDTO;
import com.example.S1_Proyecto.dto.response.UsuarioResponseDTO;
import com.example.S1_Proyecto.mapper.BodegaMapper;
import com.example.S1_Proyecto.mapper.ProductoMapper;
import com.example.S1_Proyecto.mapper.UsuarioMapper;
import com.example.S1_Proyecto.modelo.Bodega;
import com.example.S1_Proyecto.modelo.Producto;
import com.example.S1_Proyecto.repository.BodegaRepository;
import com.example.S1_Proyecto.repository.ProductoRepository;
import com.example.S1_Proyecto.repository.UsuarioRepository;
import com.example.S1_Proyecto.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    private final BodegaRepository bodegaRepository;
    private final BodegaMapper bodegaMapper;

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        Bodega b = bodegaRepository.findById(dto.bodegaId())
                .orElseThrow(() -> new RuntimeException("Error, no existe la bodega"));

        UsuarioResponseDTO dtoU = usuarioMapper.entidadADTO(
                usuarioRepository.findById(b.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("No existe el usuario"))
        );

        Producto p = productoMapper.DTOAentidad(dto, b);
        Producto pInsert = productoRepository.save(p);

        BodegaResponseDTO dtoB = bodegaMapper.entidadADTO(b, dtoU);
        return productoMapper.entidadADTO(pInsert, dtoB);
    }

    @Override
    public ProductoResponseDTO actualizarProducto(ProductoRequestDTO dto, Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: no existe dicho Producto"));

        Bodega b = bodegaRepository.findById(dto.bodegaId())
                .orElseThrow(() -> new RuntimeException("Error: no existe "));

        UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(
                usuarioRepository.findById(b.getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Error: no existe"))
        );

        productoMapper.actualizarEntidadDesdeDTO(p, dto, b);
        Producto p_actualizado = productoRepository.save(p);

        BodegaResponseDTO dtoBodega = bodegaMapper.entidadADTO(b, dtoUsuario);

        return productoMapper.entidadADTO(p_actualizado, dtoBodega);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> listarProductos() {
        return productoRepository.findAll().stream().map(dato -> {
            UsuarioResponseDTO dtoUsuario = usuarioMapper.entidadADTO(
                    usuarioRepository.findById(dato.getBodega().getUsuario().getId())
                            .orElseThrow(() -> new RuntimeException("Error: no existe el usuario"))
            );

            BodegaResponseDTO dtoBodega = bodegaMapper.entidadADTO(
                    bodegaRepository.findById(dato.getBodega().getId())
                            .orElseThrow(() -> new RuntimeException("Error: no existe la bodega")),
                    dtoUsuario
            );

            return productoMapper.entidadADTO(dato, dtoBodega);
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: no existe dicho Producto"));

        UsuarioResponseDTO dtoU = usuarioMapper.entidadADTO(
                usuarioRepository.findById(p.getBodega().getUsuario().getId())
                        .orElseThrow(() -> new RuntimeException("Error"))
        );

        BodegaResponseDTO dtoB = bodegaMapper.entidadADTO(
                bodegaRepository.findById(p.getBodega().getId())
                        .orElseThrow(() -> new RuntimeException("Error")),
                dtoU
        );

        return productoMapper.entidadADTO(p, dtoB);
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: no existe el Producto a eliminar");
        }
        productoRepository.deleteById(id);
    }
}