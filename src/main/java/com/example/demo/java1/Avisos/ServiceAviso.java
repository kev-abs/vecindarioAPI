package com.example.demo.java1.Avisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ServiceAviso {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Listar todos los avisos
    public List<AvisosDTO> obtenerAvisos() {
        String sql = "SELECT * FROM aviso ORDER BY fecha_creacion DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    // Obtener aviso por id
    public AvisosDTO obtenerAvisoPorId(int id) {
        String sql = "SELECT * FROM aviso WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> mapRow(rs));
        } catch (Exception e) {
            return null;
        }
    }

    // Insertar nuevo aviso
    public int insertarAviso(AvisosDTO aviso) {
        String sql = "INSERT INTO aviso (titulo, descripcion, categoria, estado, fecha_creacion, usuario_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        LocalDateTime fecha = aviso.getFechaCreacion() != null ? aviso.getFechaCreacion() : LocalDateTime.now();
        Timestamp ts = Timestamp.valueOf(fecha);
        return jdbcTemplate.update(sql,
                aviso.getTitulo(),
                aviso.getDescripcion(),
                aviso.getCategoria(),
                aviso.getEstado() != null ? aviso.getEstado() : "ACTIVO",
                ts,
                aviso.getUsuarioId()
        );
    }

    // Actualizar aviso (título/descripcion/categoria/estado)
    public int actualizarAviso(int id, AvisosDTO aviso) {
        String sql = "UPDATE aviso SET titulo = ?, descripcion = ?, categoria = ?, estado = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                aviso.getTitulo(),
                aviso.getDescripcion(),
                aviso.getCategoria(),
                aviso.getEstado(),
                id
        );
    }

    // Marcar como atendido (helper)
    public int marcarAtendido(int id) {
        String sql = "UPDATE aviso SET estado = 'ATENDIDO' WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Eliminar aviso
    public int eliminarAviso(int id) {
        String sql = "DELETE FROM aviso WHERE id = ?";
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
