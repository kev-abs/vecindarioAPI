package com.example.demo.java1.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConexionControllerUsuario {
    @Autowired
    private ConexionServiceUsuario conexionServiceUsuario;

    @GetMapping("/usuario")
    public List<String> obtenerUsuario() {return conexionServiceUsuario.obtenerUsuario();}



}

