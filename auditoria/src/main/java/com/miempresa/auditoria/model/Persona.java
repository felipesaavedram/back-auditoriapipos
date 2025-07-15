package com.miempresa.auditoria.model;

import jakarta.persistence.*;

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(name = "rut_ofuscado")
    private String rutOfuscado;

    @Transient
    private String rut;  // Transitorio para recibir y luego ofuscar

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "religion_id", nullable = false)
    private Religion religion;

    // Getters y setters
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

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRutOfuscado() {
        return rutOfuscado;
    }
    public void setRutOfuscado(String rutOfuscado) {
        this.rutOfuscado = rutOfuscado;
    }

    public String getRut() {
        return rut;
    }
    public void setRut(String rut) {
        this.rut = rut;
    }

    public Religion getReligion() {
        return religion;
    }
    public void setReligion(Religion religion) {
        this.religion = religion;
    }
}