package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.modelos.Compra;

public class ValidadorPago extends ManejadorBaseCompra {

    @Override
    public boolean procesar(Compra compra) {

        if (compra.getEstado() != EstadoCompras.PAGADA) {
            System.out.println("Pago no confirmado");
            return false;
        }
        System.out.println("Pago validado");
        return procesarSiguiente(compra);
    }
}