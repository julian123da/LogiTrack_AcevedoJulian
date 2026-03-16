package com.example.S1_Proyecto.repository;


import com.example.S1_Proyecto.modelo.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

}