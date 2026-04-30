package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.interfaces.creacion.Pasarela;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Compra implements Pasarela {
    private int id;
    private Usuario usuarioAsociado;
    private Evento eventoAsociado;
    private Date fechaCompra;
    private double valor;
    private EstadoCompras estado;
    private ArrayList<Entrada> entradas;
    private ArrayList<String> conjuntoItems;
    private ArrayList<String> serviciosAdicionales;

    public Compra(int id, Usuario usuarioAsociado, float valor, Evento eventoAsociado, Date fechaCompra, EstadoCompras estado, ArrayList<String> conjuntoItems, ArrayList<String> serviciosAdicionales, ArrayList<Entrada> entradas) {
        this.id = id;
        this.usuarioAsociado = usuarioAsociado;
        this.valor = valor;
        this.eventoAsociado = eventoAsociado;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
        this.conjuntoItems = conjuntoItems;
        this.serviciosAdicionales = serviciosAdicionales;
        this.entradas  = entradas;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuarioAsociado() {
        return usuarioAsociado;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public Evento getEventoAsociado() {
        return eventoAsociado;
    }

    public double getValor() {
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

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setValor(double valor) {
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

    public ArrayList<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<Entrada> entradas) {
        this.entradas = entradas;
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
