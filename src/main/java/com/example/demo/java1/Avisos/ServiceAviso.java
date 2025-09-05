package com.example.demo.java1.Avisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceAviso {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<AvisosDTO> obtenerAvisos() {
        String sql = "SELECT * FROM aviso ORDER BY fecha_creacion DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    public AvisosDTO obtenerAvisoPorId(int id) {
        String sql = "SELECT * FROM aviso WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> mapRow(rs));
        } catch (Exception e) {
            return null;
        }
    }

    public int insertarAviso(AvisosDTO aviso) {
        String sql = "INSERT INTO aviso (titulo, descripcion, categoria, estado, usuario_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, aviso.getTitulo(), aviso.getDescripcion(), aviso.getCategoria(), aviso.getEstado(), aviso.getUsuarioId());

        // Obtener el ID del aviso recién insertado
        Long avisoId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        // Consultar nombre del usuario
        String nombreUsuario = jdbcTemplate.queryForObject(
                "SELECT nombre FROM usuario WHERE id = ?",
                new Object[]{aviso.getUsuarioId()},
                String.class
        );

        // Crear notificación con estado (categoria) + nombre usuario + título
        String notiSql = "INSERT INTO notificacion (mensaje, usuario_id, aviso_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(notiSql,
                aviso.getCategoria() + " de " + nombreUsuario + ": " + aviso.getTitulo(),
                aviso.getUsuarioId(),
                avisoId
        );

        return Math.toIntExact(avisoId);
    }


    public int actualizarAviso(int id, AvisosDTO aviso) {
        String sql = "UPDATE aviso SET titulo = ?, descripcion = ?, categoria = ?, estado = ?, usuario_id = ? WHERE id = ?";
        int filas = jdbcTemplate.update(sql,
                aviso.getTitulo(),
                aviso.getDescripcion(),
                aviso.getCategoria(),
                aviso.getEstado(),
                aviso.getUsuarioId(),
                aviso.getId()
        );

        if (filas > 0) {
            String nombreUsuario = jdbcTemplate.queryForObject(
                    "SELECT nombre FROM usuario WHERE id = ?",
                    new Object[]{aviso.getUsuarioId()},
                    String.class
            );

            // Actualizar la notificación vinculada
            String notiSql = "UPDATE notificacion SET mensaje = ? WHERE aviso_id = ?";
            jdbcTemplate.update(notiSql,
                    aviso.getCategoria() + " de " + nombreUsuario + ": " + aviso.getTitulo(),
                    aviso.getId()
            );
        }

        return filas;
    }


    // Marcar como atendido
    public int marcarAtendido(int id) {
        String sql = "UPDATE aviso SET estado = 'ATENDIDO' WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Mapear ResultSet a DTO
    private AvisosDTO mapRow(ResultSet rs) throws SQLException {
        Timestamp t = rs.getTimestamp("fecha_creacion");
        LocalDateTime fecha = t != null ? t.toLocalDateTime() : null;
        return new AvisosDTO(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("categoria"),
                rs.getString("estado"),
                fecha,
                rs.getObject("usuario_id") != null ? rs.getInt("usuario_id") : null
        );
    }
}
