package com.example.demo.java1.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ConexionServiceUsuario {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener lista de usuarios
    public List<Usuario> obtenerUsuarios() {
        String sql = "SELECT * FROM usuario";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapUsuario(rs));
    }


    // Login (validar email y password)
    public Usuario login(String email, String password) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email, password}, (rs, rowNum) -> mapUsuario(rs));
        } catch (Exception e) {
            return null;
        }
    }

    // Mapeo ResultSet → Usuario
    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId((int) rs.getLong("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        usuario.setDireccion(rs.getString("direccion"));
        usuario.setPassword(rs.getString("password"));
        usuario.setRol(rs.getString("rol"));
        return usuario;
    }
    //Post registro
    public int insertarUsuarios(Usuario usuario) {
        String sql = "Insert into usuario (nombre, email, direccion, password, rol) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, usuario.getNombre(), usuario.getEmail(), usuario.getDireccion(), usuario.getPassword(), usuario.getRol());
    }

    // Actualizar usuario por ID
    public int actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, email = ?, direccion = ?, password = ?, rol = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getDireccion(),
                usuario.getPassword(),
                usuario.getRol(),
                usuario.getId()
        );
    }

    // Eliminar usuario por ID
    public int eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }


}
