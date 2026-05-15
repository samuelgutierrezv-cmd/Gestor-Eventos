package org.samuel.gestor_eventos.interfaces.comportamiento;

public class PagoNequi implements Strategy {

    @Override
    public boolean pago(double monto) {

        System.out.println("Procesando pago con Nequi: $" + monto);

        return true;
    }
}