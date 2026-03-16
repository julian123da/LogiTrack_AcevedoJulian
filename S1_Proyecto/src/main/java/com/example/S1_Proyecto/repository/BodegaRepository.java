package com.example.S1_Proyecto.repository;


import com.example.S1_Proyecto.modelo.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {

    boolean existsByUsuarioId(Long id);

}