package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.modelos.Compra;

public class CompraService {

    public void realizarCompra(Compra compra){

        System.out.println("Realizando compra para el usuario: " + compra.getUsuarioAsociado().getNombre());

        System.out.println("Evento seleccionado: " + compra.getEventoAsociado().getNombre());

        System.out.println("Cantidad de entradas: " + compra.getEntradas().size());

        System.out.println("Total actual de la compra: $" + compra.definirValorTotal());
    }
}