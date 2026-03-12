package com.example.ProyectoS1_Julian.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entidad;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Operacion operacion;
    @Column(nullable = false)
    private LocalDateTime fecha;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @Column(columnDefinition = "TEXT")
    private String valorAnterior;
    @Column(columnDefinition = "TEXT")
    private String valorNuevo;

}