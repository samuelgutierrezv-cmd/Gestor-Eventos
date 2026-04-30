package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoEntrada;
import org.samuel.gestor_eventos.interfaces.creacion.Pasarela;

public class Entrada implements Pasarela {
    private int id;
    private Zona zona;
    private Asiento asiento;
    private double precioFinal;
    private EstadoEntrada estado;

    public Entrada(int id, Zona zona, Asiento asiento, double precioFinal, EstadoEntrada estado) {
        this.id = id;
        this.zona = zona;
        this.asiento = asiento;
        this.precioFinal = precioFinal;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public EstadoEntrada getEstado() {
        return estado;
    }

    public Zona getZona() {
        return zona;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public void setEstado(EstadoEntrada estado) {
        this.estado = estado;
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
