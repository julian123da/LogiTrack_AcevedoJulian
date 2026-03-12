package com.example.ProyectoS1_Julian.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalleMovimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idMovimiento", nullable = false)
    private Movimiento movimiento;
    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;
    @Column(nullable = false)
    private Integer cantidad;

}