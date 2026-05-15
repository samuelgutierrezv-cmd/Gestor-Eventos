package org.samuel.gestor_eventos.interfaces.estructura;

import java.util.ArrayList;

public class DecoratorParqueadero extends Decorator {

    public DecoratorParqueadero(CompraInterface compraInterface) {
        super(compraInterface);
    }

    @Override
    public double definirValorTotal(){
        return compraInterface.definirValorTotal() + 50000.0;
    }

    @Override
    public ArrayList<String> definirServiciosAdiccionales() {
        ArrayList<String> servicios = compraInterface.definirServiciosAdiccionales();
        servicios.add("Servicio adiccional de parqueadero");
        return servicios;
    }
}