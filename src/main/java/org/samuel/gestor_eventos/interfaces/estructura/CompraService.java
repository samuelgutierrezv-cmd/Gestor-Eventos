package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.modelos.Compra;

public class CompraService {

    public void realizarCompra(Compra compra) {
        System.out.println(
                "Procesando compra #" + compra.getId()
        );
    }
}