package org.samuel.gestor_eventos.interfaces.comportamiento;

public class PagoDaviplata implements Strategy {

    @Override
    public boolean pago(double monto) {

        System.out.println("Procesando pago con Daviplata: $" + monto);

        return true;
    }
}