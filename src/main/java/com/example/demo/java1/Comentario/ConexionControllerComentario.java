package com.example.demo.java1.Comentario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ConexionControllerComentario {

    @Autowired
    private ConexionServiceComentario service;

    // Listar todos
    @GetMapping
    public List<Comentario> listarComentarios() {
        return service.obtenerComentarios();
    }

    // Crear
    @PostMapping
    public String crearComentario(@RequestBody Comentario comentario) {
        int result = service.crearComentario(comentario);
        return result == 1 ? "Comentario creado exitosamente" : "Error al crear comentario";
    }

    // Actualizar
    @PutMapping("/{id}")
    public String actualizarComentario(@PathVariable int id, @RequestBody Comentario comentario) {
        comentario.setId(id);
        int result = service.actualizarComentario(comentario);
        return result == 1 ? "Comentario actualizado exitosamente" : "Error al actualizar comentario";
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public String eliminarComentario(@PathVariable int id) {
        int result = service.eliminarComentario(id);
        return result == 1 ? "Comentario eliminado exitosamente" : "Error al eliminar comentario";
    }
}
