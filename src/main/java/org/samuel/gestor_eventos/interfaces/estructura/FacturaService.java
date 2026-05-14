package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.modelos.Compra;

public class FacturaService {

    public void generarFactura(Compra compra){

        System.out.println(
                "Factura generada para la compra #"
                        + compra.getId()
        );
    }
}