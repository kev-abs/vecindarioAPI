package com.example.demo.java1.Avisos;

import com.example.demo.java1.Sesion.Sesion;
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

        Long avisoId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        String nombreUsuario = jdbcTemplate.queryForObject(
                "SELECT nombre FROM usuario WHERE id = ?",
                new Object[]{aviso.getUsuarioId()},
                String.class
        );

        // Crear notificación en cuanto se cree un aviso
        String notiSql = "INSERT INTO notificacion (mensaje, usuario_id, aviso_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(notiSql,
                aviso.getCategoria() + " de " + nombreUsuario + ": " + aviso.getTitulo(),
                aviso.getUsuarioId(),
                avisoId
        );

        return Math.toIntExact(avisoId);
    }


    public int actualizarAviso(int id, AvisosDTO aviso) {

        if (Sesion.usuarioLogueado == null) {
            throw new RuntimeException("Debes iniciar sesión primero");
        }

        String rolUsuario = Sesion.usuarioLogueado.getRol(); // rol del logueado
        int filas;

        if ("admin".equalsIgnoreCase(rolUsuario)) {
            String sql = "UPDATE aviso SET titulo = ?, descripcion = ?, categoria = ?, estado = ?, usuario_id = ? WHERE id = ?";
            filas = jdbcTemplate.update(sql,
                    aviso.getTitulo(),
                    aviso.getDescripcion(),
                    aviso.getCategoria(),
                    aviso.getEstado(),
                    Sesion.usuarioLogueado.getId(),
                    id
            );
        } else {
            String sql = "UPDATE aviso SET titulo = ?, descripcion = ?, categoria = ?, usuario_id = ? WHERE id = ?";
            filas = jdbcTemplate.update(sql,
                    aviso.getTitulo(),
                    aviso.getDescripcion(),
                    aviso.getCategoria(),
                    Sesion.usuarioLogueado.getId(),
                    id
            );
        }

        if (filas > 0) {
            // Obtener nombre del usuario logueado
            String nombreUsuario = Sesion.usuarioLogueado.getNombre();

            // Actualizar la notificación vinculada
            String notiSql = "UPDATE notificacion SET mensaje = ? WHERE aviso_id = ?";
            jdbcTemplate.update(notiSql,
                    aviso.getCategoria() + " de " + nombreUsuario + ": " + aviso.getTitulo(),
                    id
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
