package com.example.demo.java1.Comentario;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConexionServiceComentario {

    private final JdbcTemplate jdbcTemplate;

    public ConexionServiceComentario(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Listar comentarios
    public List<Comentario> obtenerComentarios() {
        String sql = "SELECT * FROM Comentario";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Comentario.class));
    }

    // Crear comentario
    public int crearComentario(Comentario comentario) {
        String sql = "INSERT INTO Comentario (contenido, fecha, aviso_id, usuario_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                comentario.getContenido(),
                comentario.getFecha(),
                comentario.getAviso_id(),
                comentario.getUsuario_id());
    }

    // Actualizar comentario
    public int actualizarComentario(Comentario comentario) {
        String sql = "UPDATE Comentario SET contenido = ?, fecha = ?, aviso_id = ?, usuario_id = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                comentario.getContenido(),
                comentario.getFecha(),
                comentario.getAviso_id(),
                comentario.getUsuario_id(),
                comentario.getId());
    }

}
