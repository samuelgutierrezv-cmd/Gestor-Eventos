package org.samuel.gestor_eventos.modelos;

import java.time.LocalDate;

public class Incidencia {
    private String descripcion;
    private int id;
    private String tipo;
    private LocalDate fechaCreacion;
    private String entidadAfectada;

    public Incidencia(String descripcion, int id, String tipo, LocalDate fechaCreacion, String entidadAfectada) {
        this.descripcion = descripcion;
        this.id = id;
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
        this.entidadAfectada = entidadAfectada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public String getEntidadAfectada() {
        return entidadAfectada;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setEntidadAfectada(String entidadAfectada) {
        this.entidadAfectada = entidadAfectada;
    }
}
