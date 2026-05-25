package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.interfaces.comportamiento.*;
import org.samuel.gestor_eventos.modelos.Compra;

public class CompraFacade {

    private PagoService pagoService;
    private EntradaService entradaService;
    private FacturaService facturaService;
    private CompraService compraService;

    public CompraFacade(Strategy strategy) {

        this.compraService = new CompraService();
        this.entradaService = new EntradaService();
        this.facturaService = new FacturaService();
        this.pagoService = new PagoService(strategy);
    }

    public boolean realizarCompraCompleta(Compra compra) {

        compraService.realizarCompra(compra);

        entradaService.generarEntradas(compra);

        pagoService.procesarPago(compra);

        Handler disponibilidad = new ValidarDisponibilidad();

        Handler pago = new ValidadorPago();

        Handler factura = new GeneradorFacturaHandler();

        disponibilidad.setSiguiente(pago);

        pago.setSiguiente(factura);

        return disponibilidad.procesar(compra);
    }
}