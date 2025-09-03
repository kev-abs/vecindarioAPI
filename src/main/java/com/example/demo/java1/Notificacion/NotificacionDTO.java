package com.example.demo.java1.Notificacion;

import java.time.LocalDateTime;

public class NotificacionDTO {

    private int id;
    private String mensaje;
    private LocalDateTime fecha;
    private Long usuarioId;
    private Long avisoId;

    public NotificacionDTO() {}

    // Constructor con parámetros
    public NotificacionDTO(int id, String mensaje, LocalDateTime fecha, Long usuarioId, Long avisoId) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
        this.avisoId = avisoId;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getAvisoId() { return avisoId; }
    public void setAvisoId(Long avisoId) { this.avisoId = avisoId; }
}
