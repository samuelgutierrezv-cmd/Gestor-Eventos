package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.CategoriaEvento;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.interfaces.creacion.EventoComponente;

import java.sql.Date;
import java.time.LocalDate;

//implementando la interface cloneable
public class Evento implements EventoComponente, Cloneable {

    private CategoriaEvento categoria;
    private int id;
    private String nombre;
    private String actividadProgramada;
    private String descripcion;
    private String ciudad;
    private LocalDate fecha;
    private  String hora;
    private String politica;
    private Recinto recinto;
    private EstadoEvento estado;

    public Evento(CategoriaEvento categoria, int id, String nombre, String actividadProgramada, String descripcion, String ciudad, LocalDate fecha, String hora,String politica, Recinto recinto, EstadoEvento estado) {
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

    //metodo implementaso de la inteface cloneable que clona este mismo objeto
    @Override
    public Evento clone() {
        try {
            return (Evento) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // getter y setter
    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public CategoriaEvento getCategoria() {
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

    public String getHora() {
        return hora;
    }

    public String getPolitica() {
        return politica;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setCategoria(CategoriaEvento categoria) {
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

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setPolitica(String politica) {
        this.politica = politica;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    @Override
    public boolean actualizar(EventoComponente componente) {
        return false;
    }

    @Override
    public boolean elminarEvento(int id) {
        return false;
    }

    @Override
    public EventoComponente buscar(int id) {
        return null;
    }

    @Override
    public boolean guadarComponente(EventoComponente componente) {
        return false;
    }
}
