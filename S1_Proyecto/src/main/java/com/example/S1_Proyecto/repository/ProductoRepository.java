package com.example.S1_Proyecto.repository;


import com.example.S1_Proyecto.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombre(String nombre);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoria(String categoria);

    List<Producto> findByBodegaId(Long bodegaId);

    List<Producto> findByStockLessThan(Integer stock);

    boolean existsByNombre(String nombre);

}