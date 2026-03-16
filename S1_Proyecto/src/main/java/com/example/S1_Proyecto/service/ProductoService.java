// ProductoService.java
package com.example.S1_Proyecto.service;



import com.example.S1_Proyecto.dto.request.ProductoRequestDTO;
import com.example.S1_Proyecto.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO crearProducto(ProductoRequestDTO dto);
    ProductoResponseDTO actualizarProducto(ProductoRequestDTO dto, Long id);
    List<ProductoResponseDTO> listarProductos();
    ProductoResponseDTO buscarPorId(Long id);
    void eliminarProducto(Long id);
}