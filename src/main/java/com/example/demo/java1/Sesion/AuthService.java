package com.example.demo.java1.Sesion;

import com.example.demo.java1.usuario.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JdbcTemplate jdbcTemplate;

    public AuthService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean login(Usuario login) {
        try {
            String sql = "SELECT * FROM usuario WHERE email = ? AND password = ?";
            Usuario usuario = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setDireccion(rs.getString("direccion"));
                u.setPassword(rs.getString("password"));
                u.setRol(rs.getString("rol"));
                return u;
            }, login.getEmail(), login.getPassword());

            Sesion.usuarioLogueado = usuario;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        Sesion.usuarioLogueado = null;
    }
}
