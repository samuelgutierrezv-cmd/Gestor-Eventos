package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.enums.EstadoEntrada;

public class Entrada {
    private int id;
    private Zona zona;
    private Asiento asiento;
    private float precioFinal;
    private EstadoEntrada estado;

    public Entrada(int id, Zona zona, Asiento asiento, float precioFinal, EstadoEntrada estado) {
        this.id = id;
        this.zona = zona;
        this.asiento = asiento;
        this.precioFinal = precioFinal;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public float getPrecioFinal() {
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

    public void setPrecioFinal(float precioFinal) {
        this.precioFinal = precioFinal;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public void setEstado(EstadoEntrada estado) {
        this.estado = estado;
    }
}
