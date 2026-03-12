// ProductoServiceImpl.java
package com.example.ProyectoS1_Julian.service.impl;

import com.example.ProyectoS1_Julian.dto.request.ProductoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.ProductoResponseDTO;
import com.example.ProyectoS1_Julian.mapper.ProductoMapper;
import com.example.ProyectoS1_Julian.modelo.Producto;
import com.example.ProyectoS1_Julian.repository.ProductoRepository;
import com.example.ProyectoS1_Julian.service.ProductoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        Producto p = productoMapper.DTOAentidad(dto);
        Producto pInsertado = productoRepository.save(p);
        return productoMapper.entidadADTO(pInsertado);
    }

    @Override
    public ProductoResponseDTO actualizarProducto(ProductoRequestDTO dto, Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: no existe dicho Producto a actualizar"));

        productoMapper.actualizarEntidadDesdeDTO(p, dto);
        Producto pActualizado = productoRepository.save(p);
        return productoMapper.entidadADTO(pActualizado);
    }

    @Override
    public List<ProductoResponseDTO> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::entidadADTO)
                .toList();
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: no existe dicho Producto"));
        return productoMapper.entidadADTO(p);
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new EntityNotFoundException("Error: no existe el Producto a eliminar");
        }
        productoRepository.deleteById(id);
    }
}