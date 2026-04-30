package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.interfaces.creacion.Pasarela;

public class Facturas implements Pasarela {
    private Compra compra;
    private Pago pago;
    private static Facturas instancia;

    private Facturas(Compra compra, Pago pago){
        this.compra = compra;
        this.pago = pago;
    }

    public static Facturas getInstance(Compra compra,Pago pago){
        if(instancia == null){
            instancia = new Facturas(compra,pago);
        }
        return instancia;
    }

    public void facturaCCV(){

    }

    public void facturaPDF(){

    }

    @Override
    public boolean actualizar(Pasarela pasarela) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }

    @Override
    public Pasarela buscar(int id) {
        return null;
    }

    @Override
    public boolean guardar(Pasarela pasarela) {
        return false;
    }
}
