package com.example.ProyectoS1_Julian.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String categoria;
    @Column(nullable = false)
    private Double precio;
    private Integer stock_total;

    public void setStockTotal() {
    }

    public Integer getStockTotal() {
        return 0;
    }

    public void setPrecio() {
    }
}