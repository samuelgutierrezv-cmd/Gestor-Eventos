package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Pago;

public abstract class ProcesoDeCompra {
    protected ProcesoDeCompra siguiente;

    public void setSiguiente(ProcesoDeCompra siguiente) {
        this.siguiente = siguiente;
    }

    public abstract Iguales procesar(Compra Compra, Evento evento);
}
