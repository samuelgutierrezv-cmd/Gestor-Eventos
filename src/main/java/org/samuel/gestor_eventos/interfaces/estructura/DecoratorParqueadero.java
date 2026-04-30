package org.samuel.gestor_eventos.interfaces.estructura;

import java.util.ArrayList;

public class DecoratorParqueadero extends Decorator{

    public DecoratorParqueadero(CompraInterface compraInterface) {
        super(compraInterface);
    }

    @Override
    public String definirServicios() {
        return "";
    }

    @Override
    public boolean annadirServiciosAdicionales(ArrayList<String> servicios) {
        return false;
    }
}
