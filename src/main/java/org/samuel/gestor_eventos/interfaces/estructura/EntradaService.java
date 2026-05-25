package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.modelos.Compra;

public class EntradaService {

    public void generarEntradas(Compra compra){

        System.out.println(
                "Generando entradas para el evento: "
                        + compra.getEventoAsociado().getNombre()
        );
    }
}