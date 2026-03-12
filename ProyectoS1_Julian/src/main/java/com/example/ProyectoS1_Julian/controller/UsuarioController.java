package com.example.ProyectoS1_Julian.controller;

import com.example.ProyectoS1_Julian.modelo.Usuario;
import com.example.ProyectoS1_Julian.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Usuario> obtenerPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.obtenerPorUsername(username);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> obtenerPorNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = usuarioService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }
}