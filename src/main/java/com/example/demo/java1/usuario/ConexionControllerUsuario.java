package com.example.demo.java1.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class ConexionControllerUsuario {

    @Autowired
    private ConexionServiceUsuario conexionServiceUsuario;

    // GET → Listar usuarios como JSON
    @GetMapping
    public List<Usuario> obtenerUsuarios() {
        return conexionServiceUsuario.obtenerUsuarios();

    }

    // POST → Login
    @PostMapping("/login")
    public Object login(@RequestBody Usuario loginRequest) {
        Usuario usuario = conexionServiceUsuario.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (usuario != null) {
            return usuario; // Devuelve JSON con los datos del usuario
        } else {
            return "Credenciales inválidas";
        }
    }
    @PostMapping
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        int filas = conexionServiceUsuario.insertarUsuarios(usuario);
        return usuario;
    }
}
