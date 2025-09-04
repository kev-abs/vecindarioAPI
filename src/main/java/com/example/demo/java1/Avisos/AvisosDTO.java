package com.example.demo.java1.Avisos;

import java.time.LocalDateTime;

public class AvisosDTO {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String categoria; // AYUDA, ALERTA, COMPRAS, REUNIONES
    private String estado;    // ACTIVO, ATENDIDO
    private LocalDateTime fechaCreacion;
    private Integer usuarioId;

    public AvisosDTO() {}

    public AvisosDTO(Integer id, String titulo, String descripcion, String categoria,
                     String estado, LocalDateTime fechaCreacion, Integer usuarioId) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
}
