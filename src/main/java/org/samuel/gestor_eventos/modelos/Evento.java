package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.enums.Politicas;

import java.time.LocalDate;

public class Evento {
    private String categoria;
    private int id;
    private String nombre;
    private String actividadProgramada;
    private String descripcion;
    private String ciudad;
    private LocalDate fecha;
    private  int hora;
    private Politicas politica;
    private Recinto recinto;
    private EstadoEvento estado;

    public Evento(String categoria, int id, String nombre, String actividadProgramada, String descripcion, String ciudad, LocalDate fecha, int hora, Politicas politica, Recinto recinto, EstadoEvento estado) {
        this.categoria = categoria;
        this.id = id;
        this.nombre = nombre;
        this.actividadProgramada = actividadProgramada;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.hora = hora;
        this.politica = politica;
        this.recinto = recinto;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getActividadProgramada() {
        return actividadProgramada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getHora() {
        return hora;
    }

    public Politicas getPolitica() {
        return politica;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setActividadProgramada(String actividadProgramada) {
        this.actividadProgramada = actividadProgramada;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setPolitica(Politicas politica) {
        this.politica = politica;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }
}
