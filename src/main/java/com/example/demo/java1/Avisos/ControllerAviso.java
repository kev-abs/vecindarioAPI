package com.example.demo.java1.Avisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/avisos")
public class ControllerAviso {

    @Autowired
    private ServiceAviso serviceAviso;

    // Obtener todos los avisos
    @GetMapping
    public List<AvisosDTO> listarAvisos() {
        return serviceAviso.obtenerAvisos();
    }

    // Crear un nuevo aviso
    @PostMapping
    public String crearAviso(@RequestBody AvisosDTO aviso) {
        int resultado = serviceAviso.insertarAviso(
                aviso.getTitulo(),
                aviso.getDescripcion(),
                aviso.getCategoria(),
                aviso.getEstado(),
                aviso.getFechaCreacion() != null ? aviso.getFechaCreacion() : LocalDate.now(),
                aviso.getUsuarioId()
        );

        return resultado > 0 ? "✅ Aviso creado correctamente" : "❌ Error al crear aviso";
    }

    // Actualizar estado del aviso
    @PutMapping("/{id}")
    public String actualizarAviso(@PathVariable int id, @RequestBody AvisosDTO aviso) {
        int resultado = serviceAviso.actualizarAviso(id, aviso.getEstado());
        return resultado > 0 ? "✅ Aviso actualizado correctamente" : "❌ Error al actualizar aviso";
    }

    // Eliminar aviso
    @DeleteMapping("/{id}")
    public String eliminarAviso(@PathVariable int id) {
        int resultado = serviceAviso.eliminarAviso(id);
        return resultado > 0 ? "✅ Aviso eliminado correctamente" : "❌ Error al eliminar aviso";
    }
}
