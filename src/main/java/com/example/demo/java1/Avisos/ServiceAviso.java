package com.example.demo.java1.Avisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ServiceAviso {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Listar avisos
    public List<AvisosDTO> obtenerAvisos() {
        String sql = "SELECT * FROM aviso";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    // Insertar aviso
    public int insertarAviso(String titulo, String descripcion, String categoria,
                             String estado, LocalDate fechaCreacion, int usuarioId) {
        String sql = "INSERT INTO aviso (titulo, descripcion, categoria, estado, fecha_creacion, usuario_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, titulo, descripcion, categoria, estado, fechaCreacion, usuarioId);
    }

    // Actualizar aviso
    public int actualizarAviso(int id, String estado) {
        String sql = "UPDATE aviso SET estado=? WHERE id=?";
        return jdbcTemplate.update(sql, estado, id);
    }

    // Eliminar aviso
    public int eliminarAviso(int id) {
        String sql = "DELETE FROM aviso WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }

    private AvisosDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new AvisosDTO(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("categoria"),
                rs.getString("estado"),
                rs.getDate("fecha_creacion").toLocalDate(),
                rs.getInt("usuario_id")
        );
    }
}
