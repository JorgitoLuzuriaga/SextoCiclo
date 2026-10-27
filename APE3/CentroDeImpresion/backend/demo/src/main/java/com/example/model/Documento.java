package com.example.model;


public class Documento {

    private int id;
    private String nombre;
    private EstadoDocumento estado;

    public Documento(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.estado = EstadoDocumento.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public EstadoDocumento getEstado() {
        return estado;
    }

    public void setEstado(EstadoDocumento estado) {
        this.estado = estado;
    }
}
