package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.Entidades;
import org.samuel.gestor_eventos.enums.TipoIncidencia;
import org.samuel.gestor_eventos.interfaces.creacion.Pasarela;

import java.sql.Date;
import java.time.LocalDate;

public class Incidencia implements Pasarela {
    private String descripcion;
    private int id;
    private TipoIncidencia tipo;
    private Date fechaCreacion;
    private Entidades entidadAfectada;

    public Incidencia(String descripcion, int id, TipoIncidencia tipo, Date fechaCreacion, Entidades entidadAfectada) {
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

    public TipoIncidencia getTipo() {
        return tipo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public Entidades getEntidadAfectada() {
        return entidadAfectada;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(TipoIncidencia tipo) {
        this.tipo = tipo;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setEntidadAfectada(Entidades entidadAfectada) {
        this.entidadAfectada = entidadAfectada;
    }

    @Override
    public boolean actualizar(Pasarela pasarela) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }

    @Override
    public Pasarela buscar(int id) {
        return null;
    }

    @Override
    public boolean guardar(Pasarela pasarela) {
        return false;
    }
}
