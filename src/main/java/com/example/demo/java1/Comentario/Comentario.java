package com.example.demo.java1.Comentario;

import java.time.LocalDate;

public class Comentario {
    private int id;
    private String contenido;
    private LocalDate fecha;
    private int aviso_id;
    private int usuario_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getAviso_id() {
        return aviso_id;
    }

    public void setAviso_id(int aviso_id) {
        this.aviso_id = aviso_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
