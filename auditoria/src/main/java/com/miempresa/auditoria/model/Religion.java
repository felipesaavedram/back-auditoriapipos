package com.miempresa.auditoria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "religiones")
public class Religion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "religion_hash")  // aquí va el nombre real de la columna en la tabla
    private String nombre;

    // Constructor vacío
    public Religion() {}

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}