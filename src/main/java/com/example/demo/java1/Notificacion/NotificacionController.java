package com.example.demo.java1.Notificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService service;

    // Listar todas
    @GetMapping
    public List<NotificacionDTO> listarNotificaciones() {
        return service.obtenerNotificaciones();
    }

    // Crear
    @PostMapping
    public String crearNotificacion(@RequestBody NotificacionDTO notificacion) {
        int result = service.insertarNotificacion(notificacion);
        return result == 1 ? "Notificación creada exitosamente" : "Error al crear notificación";
    }

    // Actualizar
    @PutMapping("/{id}")
    public String actualizarNotificacion(@PathVariable int id, @RequestBody NotificacionDTO notificacion) {
        notificacion.setId(id);
        int result = service.actualizarNotificacion(notificacion);
        return result == 1 ? "Notificación actualizada exitosamente" : "Error al actualizar notificación";
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public String eliminarNotificacion(@PathVariable int id) {
        int result = service.eliminarNotificacion(id);
        return result == 1 ? "Notificación eliminada exitosamente" : "Error al eliminar notificación";
    }
}
