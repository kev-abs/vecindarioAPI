package com.example.demo.java1.Notificacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final List<NotificacionDTO> notificaciones = new ArrayList<>();

    // Obtener todas
    public List<NotificacionDTO> obtenerNotificaciones() {
        String sql = "SELECT id, mensaje, fecha, usuario_Id, aviso_Id FROM notificacion";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            NotificacionDTO notificacion = new NotificacionDTO();
            notificacion.setId(rs.getInt("id"));
            notificacion.setMensaje(rs.getString("mensaje"));
            notificacion.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
            notificacion.setUsuarioId((long) rs.getInt("usuario_Id"));
            notificacion.setAvisoId((long) rs.getInt("aviso_Id"));
            return notificacion;
        });
    }



    // Insertar
    public int insertarNotificacion(String mensaje, LocalDateTime fecha) {
        NotificacionDTO n = new NotificacionDTO();
        n.setId(notificaciones.size() + 1);
        n.setMensaje(mensaje);
        n.setFecha(fecha);
        notificaciones.add(n);
        return 1; // simula que se insertó
    }

    // Actualizar
    public int actualizarNotificacion(int id, String mensaje, LocalDateTime fecha) {
        for (NotificacionDTO n : notificaciones) {
            if (n.getId() == id) {
                n.setMensaje(mensaje);
                n.setFecha(fecha);
                return 1; // actualizado
            }
        }
        return 0; // no encontrado
    }

    // Eliminar
    public int eliminarNotificacion(int id) {
        return notificaciones.removeIf(n -> n.getId() == id) ? 1 : 0;
    }
}
