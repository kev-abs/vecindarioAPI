package com.example.demo.java1.Avisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avisos")
public class ControllerAviso {

    @Autowired
    private ServiceAviso serviceAviso;

    @GetMapping
    public List<AvisosDTO> listarAvisos() {
        return serviceAviso.obtenerAvisos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvisosDTO> obtenerPorId(@PathVariable int id) {
        AvisosDTO a = serviceAviso.obtenerAvisoPorId(id);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }

    @PostMapping
    public ResponseEntity<String> crearAviso(@RequestBody AvisosDTO aviso) {
        int filas = serviceAviso.insertarAviso(aviso);
        return filas > 0 ? ResponseEntity.ok("Aviso creado correctamente") :
                ResponseEntity.status(500).body("Error al crear aviso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarAviso(@PathVariable int id, @RequestBody AvisosDTO aviso) {
        int filas = serviceAviso.actualizarAviso(id, aviso);
        return filas > 0 ? ResponseEntity.ok("Aviso actualizado correctamente") :
                ResponseEntity.status(404).body("Aviso no encontrado o sin cambios");
    }

    // Marcar como atendido
    @PatchMapping("/{id}/atender")
    public ResponseEntity<String> atenderAviso(@PathVariable int id) {

        int filas = serviceAviso.marcarAtendido(id);
        return filas > 0 ? ResponseEntity.ok("Aviso marcado como ATENDIDO") :
                ResponseEntity.status(404).body("Aviso no encontrado");
    }
}
