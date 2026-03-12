// ProductoService.java
package com.example.ProyectoS1_Julian.service;

import com.example.ProyectoS1_Julian.dto.request.ProductoRequestDTO;
import com.example.ProyectoS1_Julian.dto.response.ProductoResponseDTO;
import java.util.List;

public interface ProductoService {
    ProductoResponseDTO crearProducto(ProductoRequestDTO dto);
    ProductoResponseDTO actualizarProducto(ProductoRequestDTO dto, Long id);
    List<ProductoResponseDTO> listarProductos();
    ProductoResponseDTO buscarPorId(Long id);
    void eliminarProducto(Long id);
}