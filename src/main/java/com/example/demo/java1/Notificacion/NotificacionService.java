package com.example.demo.java1.Notificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener todas las notificaciones
    public List<NotificacionDTO> obtenerNotificaciones() {
        String sql = "SELECT id, mensaje, fecha, usuario_id, aviso_id FROM notificacion";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            NotificacionDTO notificacion = new NotificacionDTO();
            notificacion.setId((int) rs.getLong("id"));
            notificacion.setMensaje(rs.getString("mensaje"));
            notificacion.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
            notificacion.setUsuarioId(rs.getLong("usuario_id"));
            notificacion.setAvisoId(rs.getLong("aviso_id"));
            return notificacion;
        });
    }

    // Insertar nueva notificación
    public int insertarNotificacion(NotificacionDTO notificacion) {
        String sql = "INSERT INTO notificacion (mensaje, fecha, usuario_id, aviso_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                notificacion.getMensaje(),
                Timestamp.valueOf(notificacion.getFecha()),
                notificacion.getUsuarioId(),
                notificacion.getAvisoId()
        );
    }

    // Actualizar notificación existente
    public int actualizarNotificacion(NotificacionDTO notificacion) {
        String sql = "UPDATE notificacion SET mensaje = ?, fecha = ?, usuario_id = ?, aviso_id = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                notificacion.getMensaje(),
                Timestamp.valueOf(notificacion.getFecha()),
                notificacion.getUsuarioId(),
                notificacion.getAvisoId(),
                notificacion.getId()
        );
    }

    // Eliminar notificación por ID
    public int eliminarNotificacion(long id) {
        String sql = "DELETE FROM notificacion WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
