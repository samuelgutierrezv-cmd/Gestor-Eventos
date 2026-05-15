package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.interfaces.comportamiento.Strategy;
import org.samuel.gestor_eventos.modelos.Compra;

public class PagoService {

    private Strategy metodosDePago;

    public PagoService(Strategy metodosDePago) {
        this.metodosDePago = metodosDePago;
    }

    public void  setMetodosDePago(Strategy metodosDePago) {
        this.metodosDePago = metodosDePago;
    }

    public void procesarPago(Compra compra){
        metodosDePago.pago(compra.getValor());
    }
}