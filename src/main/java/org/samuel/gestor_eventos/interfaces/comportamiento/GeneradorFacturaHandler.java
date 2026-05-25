package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.modelos.Compra;

public class GeneradorFacturaHandler extends ManejadorBaseCompra {

    @Override
    public boolean procesar(Compra compra) {
        System.out.println("Factura generada correctamente");
        return procesarSiguiente(compra);
    }
}