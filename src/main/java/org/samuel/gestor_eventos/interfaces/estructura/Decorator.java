package org.samuel.gestor_eventos.interfaces.estructura;

import java.util.ArrayList;

public abstract class Decorator implements CompraInterface {

    protected CompraInterface compraInterface;

    public Decorator(CompraInterface compraInterface) {
        this.compraInterface = compraInterface;
    }

    @Override
    public double definirValorTotal() {
        return compraInterface.definirValorTotal();
    }

    @Override
    public ArrayList<String> definirServiciosAdiccionales() {
        return compraInterface.definirServiciosAdiccionales();
    }
}