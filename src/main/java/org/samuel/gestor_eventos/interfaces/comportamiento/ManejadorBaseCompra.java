package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.modelos.Compra;

public abstract class ManejadorBaseCompra implements Handler {
    protected Handler siguiente;

    @Override
    public void setSiguiente(Handler handler) {
        this.siguiente = handler;
    }

    protected boolean procesarSiguiente(Compra compra) {
        if (siguiente == null) {
            return true;
        }
        return siguiente.procesar(compra);
    }
}
