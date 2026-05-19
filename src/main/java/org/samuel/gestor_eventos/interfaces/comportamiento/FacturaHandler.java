package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.interfaces.estructura.FacturaService;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;

public class FacturaHandler extends ProcesoDeCompra {

    private FacturaService facturaService;

    public FacturaHandler(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Override
    public Compra procesar(Compra compra, Evento evento) {

        facturaService.generarFactura(compra);

        if(siguiente != null){
            return siguiente.procesar(compra, evento);
        }

        return compra;
    }
}