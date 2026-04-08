package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoCompras;

import java.time.LocalDate;
import java.util.ArrayList;

public class Compra {
    private int id;
    private Usuario usuarioAsociado;
    private Evento eventoAsociado;
    private LocalDate fechaCompra;
    private float valor;
    private EstadoCompras estado;
    private ArrayList<String> conjuntoItems;
    private ArrayList<String> serviciosAdicionales;

    public Compra(int id, Usuario usuarioAsociado, float valor, Evento eventoAsociado, LocalDate fechaCompra, EstadoCompras estado, ArrayList<String> conjuntoItems, ArrayList<String> serviciosAdicionales) {
        this.id = id;
        this.usuarioAsociado = usuarioAsociado;
        this.valor = valor;
        this.eventoAsociado = eventoAsociado;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.conjuntoItems = conjuntoItems;
        this.serviciosAdicionales = serviciosAdicionales;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public Evento getEventoAsociado() {
        return eventoAsociado;
    }

    public float getValor() {
        return valor;
    }

    public ArrayList<String> getConjuntoItems() {
        return conjuntoItems;
    }

    public EstadoCompras getEstado() {
        return estado;
    }

    public ArrayList<String> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuarioAsociado(Usuario usuarioAsociado) {
        this.usuarioAsociado = usuarioAsociado;
    }

    public void setEventoAsociado(Evento eventoAsociado) {
        this.eventoAsociado = eventoAsociado;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setConjuntoItems(ArrayList<String> conjuntoItems) {
        this.conjuntoItems = conjuntoItems;
    }

    public void setEstado(EstadoCompras estado) {
        this.estado = estado;
    }

    public void setServiciosAdicionales(ArrayList<String> serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

}
