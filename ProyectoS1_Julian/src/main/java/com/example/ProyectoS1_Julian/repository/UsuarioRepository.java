package com.example.ProyectoS1_Julian.repository;

import com.example.ProyectoS1_Julian.modelo.Rol;
import com.example.ProyectoS1_Julian.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNombreIgnoreCase(String nombre);

    boolean existsByDocumento(String documento);

    List<Usuario> findByRol(Rol rol);

    Optional<Usuario> findByUsername(String username);
}