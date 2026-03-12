package com.example.ProyectoS1_Julian.repository;

import com.example.ProyectoS1_Julian.modelo.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {

}