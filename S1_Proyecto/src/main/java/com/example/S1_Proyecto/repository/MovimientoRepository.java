package com.example.S1_Proyecto.repository;


import com.example.S1_Proyecto.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
List<Movimiento> findTop10ByOrMovimientos();
}