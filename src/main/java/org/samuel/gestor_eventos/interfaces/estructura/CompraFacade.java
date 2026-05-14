package org.samuel.gestor_eventos.interfaces.estructura;

import org.samuel.gestor_eventos.modelos.Compra;

public class CompraFacade {

    private PagoService pagoService;
    private EntradaService entradaService;
    private FacturaService facturaService;
    private CompraService compraService;

    public CompraFacade() {
        this.compraService = new CompraService();
        this.entradaService = new EntradaService();
        this.facturaService = new FacturaService();
        this.pagoService = new PagoService();
    }

    public void realizarCompraCompleta(Compra compra) {

        compraService.realizarCompra(compra);
        entradaService.generarEntradas(compra);
        pagoService.procesarPago(compra);
        facturaService.generarFactura(compra);
        System.out.println("Compra completada correctamente");
    }
}