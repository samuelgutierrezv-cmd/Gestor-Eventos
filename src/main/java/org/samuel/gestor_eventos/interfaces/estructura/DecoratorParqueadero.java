package org.samuel.gestor_eventos.interfaces.estructura;

import java.util.ArrayList;

public class DecoratorParqueadero extends Decorator {

    public DecoratorParqueadero(CompraInterface compraInterface) {
        super(compraInterface);
    }

    @Override
    public double definirValorTotal() {
        return super.definirValorTotal() + 20000;
    }

    @Override
    public String definirServicios() {
        return super.definirServicios() + ", Parqueadero";
    }

    @Override
    public boolean annadirServiciosAdicionales(ArrayList<String> servicios) {

        if (servicios == null) {
            servicios = new ArrayList<>();
        }

        servicios.add("Parqueadero");

        return super.annadirServiciosAdicionales(servicios);
    }
}