package com.example.ProyectoS1_Julian.repository;

import com.example.ProyectoS1_Julian.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}