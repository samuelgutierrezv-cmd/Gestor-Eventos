package org.samuel.gestor_eventos.interfaces.comportamiento;

import org.samuel.gestor_eventos.interfaces.estructura.PagoService;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;

public class PagoHandler extends ProcesoDeCompra {

    private PagoService pagoService;

    public PagoHandler(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Override
    public Compra procesar(Compra compra, Evento evento) {

        pagoService.procesarPago(compra);

        if(siguiente != null){
            return siguiente.procesar(compra, evento);
        }

        return compra;
    }
}