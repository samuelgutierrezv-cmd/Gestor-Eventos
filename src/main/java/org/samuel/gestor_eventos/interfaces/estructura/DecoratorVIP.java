package org.samuel.gestor_eventos.interfaces.estructura;

import java.util.ArrayList;

public class DecoratorVIP extends Decorator {

    public DecoratorVIP(CompraInterface compraInterface) {
        super(compraInterface);
    }

    @Override
    public double definirValorTotal() {
        return compraInterface.definirValorTotal() + 100000.0;
    }

    @Override
    public ArrayList<String> definirServiciosAdiccionales(){
        ArrayList<String> servicio = compraInterface.definirServiciosAdiccionales();
        servicio.add("Servicio adiccional VIP");
        return servicio;
    }
}