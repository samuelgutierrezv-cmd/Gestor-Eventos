package org.samuel.gestor_eventos.modelos;

public class Facturas {
    private Compra compra;
    private static Facturas instancia;

    private Facturas(Compra compra){
        this.compra = compra;
    }

    public static Facturas getInstance(Compra compra){
        if(instancia == null){
            return new Facturas(compra);
        }else{
            return instancia;
        }
    }

    public void facturaCCV(){

    }

    public void facturaPDF(){

    }
}
