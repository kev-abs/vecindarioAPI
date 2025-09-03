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

    // GET /api/avisos -> listar todos
    @GetMapping
    public List<AvisosDTO> listarAvisos() {
        return serviceAviso.obtenerAvisos();
    }

    // GET /api/avisos/{id} -> obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<AvisosDTO> obtenerPorId(@PathVariable int id) {
        AvisosDTO a = serviceAviso.obtenerAvisoPorId(id);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }

    // POST /api/avisos -> crear aviso
    @PostMapping
    public ResponseEntity<String> crearAviso(@RequestBody AvisosDTO aviso) {
        int filas = serviceAviso.insertarAviso(aviso);
        return filas > 0 ? ResponseEntity.ok("Aviso creado correctamente") :
                ResponseEntity.status(500).body("Error al crear aviso");
    }

    // PUT /api/avisos/{id} -> actualizar aviso
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarAviso(@PathVariable int id, @RequestBody AvisosDTO aviso) {
        int filas = serviceAviso.actualizarAviso(id, aviso);
        return filas > 0 ? ResponseEntity.ok("Aviso actualizado correctamente") :
                ResponseEntity.status(404).body("Aviso no encontrado o sin cambios");
    }

    // PATCH /api/avisos/{id}/atender -> marcar como atendido
    @PatchMapping("/{id}/atender")
    public ResponseEntity<String> atenderAviso(@PathVariable int id) {
        int filas = serviceAviso.marcarAtendido(id);
        return filas > 0 ? ResponseEntity.ok("Aviso marcado como ATENDIDO") :
                ResponseEntity.status(404).body("Aviso no encontrado");
    }

    // DELETE /api/avisos/{id} -> eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAviso(@PathVariable int id) {
        int filas = serviceAviso.eliminarAviso(id);
        return filas > 0 ? ResponseEntity.ok("Aviso eliminado correctamente") :
                ResponseEntity.status(404).body("Aviso no encontrado");
    }
}
