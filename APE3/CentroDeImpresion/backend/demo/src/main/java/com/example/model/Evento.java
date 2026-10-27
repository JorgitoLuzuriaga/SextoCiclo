package com.example.model;



import java.time.LocalDateTime;

public class Evento {

    private LocalDateTime fecha;
    private int documentoId;
    private String etapa;
    private String mensaje;

    public Evento(int documentoId, String etapa, String mensaje) {
        this.fecha = LocalDateTime.now();
        this.documentoId = documentoId;
        this.etapa = etapa;
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getDocumentoId() {
        return documentoId;
    }

    public String getEtapa() {
        return etapa;
    }

    public String getMensaje() {
        return mensaje;
    }
}
