package org.samuel.gestor_eventos.interfaces.estructura;

import java.util.ArrayList;

public abstract class Decorator implements  CompraInterface{
    private CompraInterface compraInterface;

    public Decorator(CompraInterface compraInterface){
        this.compraInterface = compraInterface;
    }

    @Override
    public ArrayList<String> getServiciosAdicionales(){
        return compraInterface.getServiciosAdicionales();
    }

    @Override
    public double definirValorTotal(){
        double valor = 0;
        if(compraInterface.getServiciosAdicionales().size() == 1){
            valor =  2000000;
        }else{
            for(String e : compraInterface.getServiciosAdicionales()) {
                if (e == "parqueadero") {
                    valor =+10000000;
                } else if(e == "vip") {
                    valor =+ 10000000;
                }
            }
        }
        return valor;
    }

}
