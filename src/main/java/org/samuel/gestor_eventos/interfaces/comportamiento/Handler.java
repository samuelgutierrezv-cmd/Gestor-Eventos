package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.modelos.Compra;

public interface Handler {
    void setSiguiente(Handler handler);
    boolean procesar(Compra compra);
}