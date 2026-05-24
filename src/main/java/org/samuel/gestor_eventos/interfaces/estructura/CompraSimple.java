package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.modelos.Compra;
import java.util.ArrayList;

public class CompraSimple implements CompraInterface {
    private Compra compra;

    public CompraSimple(Compra compra) {
        this.compra = compra;
    }

    @Override
    public double definirValorTotal() {
        return compra.getValor();
    }

    @Override
    public ArrayList<String> definirServiciosAdiccionales() {
        return compra.getServiciosAdicionales();
    }
}