package com.example.demo.java1.Notificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Obtener todas las notificaciones
    @GetMapping
    public List<NotificacionDTO> listarNotificaciones() {
        return notificacionService.obtenerNotificaciones();
    }

    // Crear nueva notificación
    @PostMapping
    public String crearNotificacion(@RequestBody NotificacionDTO notificacion) {
        if (notificacion.getMensaje() == null || notificacion.getMensaje().trim().isEmpty()) {
            return "Error: El mensaje no puede estar vacío";
        }

        int filas = notificacionService.insertarNotificacion(notificacion);

        return (filas > 0)
                ? "Notificación agregada correctamente"
                : "Error al agregar notificación";
    }

    // Actualizar notificación existente
    @PutMapping("/{id}")
    public String actualizarNotificacion(@PathVariable long id, @RequestBody NotificacionDTO notificacion) {
        notificacion.setId((int) id); // asignamos el id del path al DTO
        int filas = notificacionService.actualizarNotificacion(notificacion);

        return (filas > 0)
                ? "Notificación actualizada correctamente"
                : "Notificación no encontrada o sin cambios";
    }

}
