package com.example.ProyectoS1_Julian.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_movimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimiento_id", nullable = false)
    private Movimiento movimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}