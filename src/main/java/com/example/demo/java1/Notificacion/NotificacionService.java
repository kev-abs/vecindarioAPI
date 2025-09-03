package com.example.demo.java1.Notificacion;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    private final JdbcTemplate jdbcTemplate;

    public NotificacionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Listar todas las notificaciones
    public List<NotificacionDTO> obtenerNotificaciones() {
        String sql = "SELECT * FROM notificacion ORDER BY fecha DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(NotificacionDTO.class));
    }

    // Crear notificación
    public int insertarNotificacion(NotificacionDTO notificacion) {
        String sql = "INSERT INTO notificacion (mensaje, fecha, usuario_id, aviso_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                notificacion.getMensaje(),
                notificacion.getFecha(),
                notificacion.getUsuarioId(),
                notificacion.getAvisoId()
        );
    }

    // Actualizar notificación
    public int actualizarNotificacion(NotificacionDTO notificacion) {
        String sql = "UPDATE notificacion SET mensaje = ?, fecha = ?, usuario_id = ?, aviso_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                notificacion.getMensaje(),
                notificacion.getFecha(),
                notificacion.getUsuarioId(),
                notificacion.getAvisoId(),
                notificacion.getId()
        );
    }

    // Eliminar notificación
    public int eliminarNotificacion(int id) {
        String sql = "DELETE FROM notificacion WHERE id = ?";
        return 0;
    }
}
