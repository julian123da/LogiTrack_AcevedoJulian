package com.example.ProyectoS1_Julian.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idBodega", nullable = false)
    private Bodega bodega;
    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;
    @Column(nullable = false)
    private Integer cantidad;

}