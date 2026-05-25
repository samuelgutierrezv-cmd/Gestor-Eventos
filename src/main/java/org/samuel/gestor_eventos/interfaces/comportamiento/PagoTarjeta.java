package org.samuel.gestor_eventos.interfaces.comportamiento;

public class PagoTarjeta implements Strategy {

    @Override
    public boolean pago(double monto) {

        System.out.println("Procesando pago con tarjeta: $" + monto);

        return true;
    }
}