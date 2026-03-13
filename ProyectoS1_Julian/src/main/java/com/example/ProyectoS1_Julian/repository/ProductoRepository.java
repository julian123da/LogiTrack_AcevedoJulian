package com.example.ProyectoS1_Julian.repository;

import com.example.ProyectoS1_Julian.modelo.Producto;
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