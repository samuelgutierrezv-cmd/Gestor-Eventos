package org.samuel.gestor_eventos.interfaces.estructura;

import java.util.ArrayList;

public class DecoratorVIP extends Decorator {

    public DecoratorVIP(CompraInterface compraInterface) {
        super(compraInterface);
    }

    @Override
    public double definirValorTotal() {
        return super.definirValorTotal() + 50000;
    }

    @Override
    public String definirServicios() {
        return super.definirServicios() + ", VIP";
    }

    @Override
    public boolean annadirServiciosAdicionales(ArrayList<String> servicios) {

        if (servicios == null) {
            servicios = new ArrayList<>();
        }

        servicios.add("VIP");

        return super.annadirServiciosAdicionales(servicios);
    }
}