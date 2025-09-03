package com.example.demo.java1.Notificacion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificacionController {

    @Autowired
    private NotificacionService conexionService;

    // Listar notificaciones
    @GetMapping("/notificaciones")
    public List<NotificacionDTO> listarNotificaciones() {
        return conexionService.obtenerNotificaciones();
    }

    // Crear notificación
    @PostMapping("/notificaciones")
    public String crearNotificacion(@RequestBody NotificacionDTO notificacion) {
        if (notificacion.getMensaje() == null || notificacion.getMensaje().trim().isEmpty()) {
            return "Error: El mensaje no puede estar vacío";
        }

        int filas = conexionService.insertarNotificacion(
                notificacion.getMensaje(),
                notificacion.getFecha()
        );

        return (filas > 0) ? "Notificación agregada correctamente" : "Error al agregar notificación";
    }

    // Actualizar notificación
    @PutMapping("/notificaciones/{id}")
    public String actualizarNotificacion(@PathVariable int id, @RequestBody NotificacionDTO notificacion) {
        int filas = conexionService.actualizarNotificacion(
                id,
                notificacion.getMensaje(),
                notificacion.getFecha()
        );

        return (filas > 0) ? "Notificación actualizada correctamente" : "Notificación no encontrada o sin cambios";
    }

    // Eliminar notificación
    @DeleteMapping("/notificaciones/{id}")
    public String eliminarNotificacion(@PathVariable int id) {
        int filas = conexionService.eliminarNotificacion(id);
        return (filas > 0)
                ? "Notificación eliminada correctamente"
                : "Notificación no encontrada";
    }
}
