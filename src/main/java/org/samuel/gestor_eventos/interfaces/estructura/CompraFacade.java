package org.samuel.gestor_eventos.interfaces.estructura;

import com.almasb.fxgl.core.collection.Array;
import org.samuel.gestor_eventos.interfaces.comportamiento.Iguales;
import org.samuel.gestor_eventos.interfaces.comportamiento.Strategy;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;

import java.util.ArrayList;

public class CompraFacade {

    private PagoService pagoService;
    private EntradaService entradaService;
    private FacturaService facturaService;
    private CompraService compraService;

    public CompraFacade(Strategy estructura) {
        this.compraService = new CompraService();
        this.entradaService = new EntradaService();
        this.facturaService = new FacturaService();
        this.pagoService = new PagoService( estructura);
    }

    public Iguales realizarCompraCompleta(Compra compra, Evento evento) {
        
        ArrayList<Iguales> iguales =  new ArrayList<>();
        return (Iguales) iguales;
    }
}