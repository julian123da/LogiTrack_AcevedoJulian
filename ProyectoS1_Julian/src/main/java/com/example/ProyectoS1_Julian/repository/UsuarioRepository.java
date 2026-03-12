package com.example.ProyectoS1_Julian.repository;

import com.example.ProyectoS1_Julian.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsuario(String usuario);

    List<Usuario> findByNombreIgnoreCase(String nombre);

}