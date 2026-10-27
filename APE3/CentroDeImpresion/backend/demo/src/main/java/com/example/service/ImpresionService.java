package com.example.service;


import com.example.model.Documento;
import com.example.model.EstadoDocumento;
import com.example.model.Evento;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

@Service
public class ImpresionService {

    private final List<Documento> documentos =
            Collections.synchronizedList(new ArrayList<>());

    private final List<Evento> eventos =
            Collections.synchronizedList(new ArrayList<>());

    // Semáforos para coordinar las tres etapas
private Semaphore turnoDiseno = new Semaphore(1);
private Semaphore turnoRevision = new Semaphore(0);
private Semaphore turnoImpresion = new Semaphore(0);

    private volatile boolean ejecutando = false;

    public synchronized boolean iniciarSimulacion() {

        if (ejecutando) {
            return false;
        }

        ejecutando = true;
        documentos.clear();
        eventos.clear();

        // Se crearán tres documentos
        for (int i = 1; i <= 3; i++) {
            documentos.add(new Documento(i, "Documento " + i));
        }

        // Reiniciar los semáforos para una nueva ejecución
        turnoDiseno = new Semaphore(1);
        turnoRevision = new Semaphore(0);
        turnoImpresion = new Semaphore(0);

        Thread procesoDiseno = new Thread(this::ejecutarDiseno);
        Thread procesoRevision = new Thread(this::ejecutarRevision);
        Thread procesoImpresion = new Thread(this::ejecutarImpresion);

        procesoDiseno.setName("Proceso-Diseno");
        procesoRevision.setName("Proceso-Revision");
        procesoImpresion.setName("Proceso-Impresion");

        procesoDiseno.start();
        procesoRevision.start();
        procesoImpresion.start();

        return true;
    }

    private void ejecutarDiseno() {
        try {
            for (Documento documento : documentos) {
                turnoDiseno.acquire();

                documento.setEstado(EstadoDocumento.DISENANDO);
                registrar(documento, "DISEÑO",
                        "Iniciando diseño del documento");

                Thread.sleep(1000);

                registrar(documento, "DISEÑO",
                        "Diseño terminado");

                turnoRevision.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void ejecutarRevision() {
        try {
            for (Documento documento : documentos) {
                turnoRevision.acquire();

                documento.setEstado(EstadoDocumento.EN_REVISION);
                registrar(documento, "REVISIÓN",
                        "Iniciando revisión del documento");

                Thread.sleep(1000);

                registrar(documento, "REVISIÓN",
                        "Revisión terminada");

                turnoImpresion.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void ejecutarImpresion() {
        try {
            for (Documento documento : documentos) {
                turnoImpresion.acquire();

                documento.setEstado(EstadoDocumento.IMPRIMIENDO);
                registrar(documento, "IMPRESIÓN",
                        "Imprimiendo documento");

                Thread.sleep(1000);

                documento.setEstado(EstadoDocumento.COMPLETADO);
                registrar(documento, "IMPRESIÓN",
                        "Documento impreso correctamente");

                // Permite comenzar el diseño del siguiente documento
                turnoDiseno.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            ejecutando = false;
        }
    }

    private void registrar(
            Documento documento,
            String etapa,
            String mensaje) {

        eventos.add(new Evento(
                documento.getId(),
                etapa,
                mensaje
        ));
    }

    public List<Documento> obtenerDocumentos() {
        synchronized (documentos) {
            return new ArrayList<>(documentos);
        }
    }

    public List<Evento> obtenerEventos() {
        synchronized (eventos) {
            return new ArrayList<>(eventos);
        }
    }

    public boolean estaEjecutando() {
        return ejecutando;
    }
}
