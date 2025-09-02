package com.example.demo.java1.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class ConexionServiceUsuario {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> obtenerUsuario() {
        String sql = "SELECT * FROM usuario";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("id") + " " +
                        rs.getString("nombre") + " " +
                        rs.getString("email") + " " +
                        rs.getString("direccion") + " " +
                        rs.getString("password") + " " +
                        rs.getString("rol");
            }
        });

    }

}

