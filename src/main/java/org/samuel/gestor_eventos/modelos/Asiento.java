package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.interfaces.creacion.EventoComponente;

public class Asiento implements EventoComponente {
    private int id;
    private int fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(int numero, EstadoAsiento estado, int fila, int id) {
        this.numero = numero;
        this.estado = estado;
        this.fila = fila;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public int getFila() {
        return fila;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setEstado(EstadoAsiento estado) {
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
