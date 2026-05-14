package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.modelos.Compra;

public class PagoService {

    public void procesarPago(Compra compra){

        System.out.println(
                "Procesando pago de: $" +
                        compra.definirValorTotal()
        );
    }
}