package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.modelos.Compra;

public class ValidarDisponibilidad extends ManejadorBaseCompra {

    @Override
    public boolean procesar(Compra compra) {
        if (compra.getEntradas() == null || compra.getEntradas().isEmpty()) {
            System.out.println("No hay entradas disponibles");
            return false;
        }
        System.out.println("Disponibilidad validada");
        return procesarSiguiente(compra);
    }
}