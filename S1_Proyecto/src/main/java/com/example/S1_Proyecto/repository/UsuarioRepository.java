package com.example.S1_Proyecto.repository;


import com.example.S1_Proyecto.modelo.Rol;
import com.example.S1_Proyecto.modelo.Usuario;
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