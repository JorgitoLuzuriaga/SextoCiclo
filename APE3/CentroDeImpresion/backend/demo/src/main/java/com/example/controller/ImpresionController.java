package com.example.controller;


import com.example.service.ImpresionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/impresion")
@CrossOrigin(origins = "http://localhost:5173")
public class ImpresionController {

    private final ImpresionService impresionService;

    public ImpresionController(ImpresionService impresionService) {
        this.impresionService = impresionService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Map<String, String>> iniciar() {

        boolean iniciado = impresionService.iniciarSimulacion();

        if (!iniciado) {
            return ResponseEntity.status(409).body(
                    Map.of("mensaje",
                            "La simulación ya está en ejecución")
            );
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Simulación iniciada")
        );
    }

    @GetMapping("/documentos")
    public ResponseEntity<?> obtenerDocumentos() {
        return ResponseEntity.ok(
                impresionService.obtenerDocumentos()
        );
    }

    @GetMapping("/eventos")
    public ResponseEntity<?> obtenerEventos() {
        return ResponseEntity.ok(
                impresionService.obtenerEventos()
        );
    }

    @GetMapping("/estado")
    public ResponseEntity<?> obtenerEstado() {
        return ResponseEntity.ok(
                Map.of("ejecutando",
                        impresionService.estaEjecutando())
        );
    }
}
