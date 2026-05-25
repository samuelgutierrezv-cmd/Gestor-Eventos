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

    // Funciones basicas

    @Override
    public boolean actualizar(EventoComponente componente) {
        if (componente instanceof Asiento) {
            Asiento a = (Asiento) componente;
            if (this.id == a.id) {
                this.numero = a.numero;
                this.fila = a.fila;
                this.estado = a.estado;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarEvento(int id) {
        return this.id == id;
    }

    @Override
    public EventoComponente buscar(int id) {
        return this.id == id ? this : null;
    }

    @Override
    public boolean guardarComponente(EventoComponente componente) {
        return false;
    }


    public void cambiarEstado(EstadoAsiento nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("Estado inválido");
        }
        if (this.estado == nuevoEstado) {
            throw new IllegalStateException("Ya tiene ese estado");
        }
        this.estado = nuevoEstado;
    }

    public boolean estaDisponible() {
        return this.estado == EstadoAsiento.DISPONIBLE;
    }
}
