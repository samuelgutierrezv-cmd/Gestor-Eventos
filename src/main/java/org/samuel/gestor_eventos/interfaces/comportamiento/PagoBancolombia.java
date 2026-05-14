package org.samuel.gestor_eventos.interfaces.comportamiento;

public class PagoBancolombia implements Strategy {

    @Override
    public boolean pago(double monto) {

        System.out.println("Procesando pago con Bancolombia: $" + monto);

        return true;
    }
}