package org.samuel.gestor_eventos.interfaces.estructura;

public class CompraFacade {
    private PagoService pagoService;
    private EntradaService entradaService;
    private FacturaService facturaService;
    private CompraService compraService;

    public CompraFacade(){
        this.compraService = new CompraService();
        this.entradaService = new EntradaService();
        this.facturaService =  new FacturaService();
        this.pagoService = new PagoService();
    }

    public void realizarPasarelaPedido(){
        /* Ejemplo para cuando lo vallamos a hacer que esta es una de las partes mas importantes
            como se usa es como la parte del proceso y las clases compra y de mas solo sirven para algunos metodos
            y fuardar informacion de resto lo demas se hace aqui.
        * */

        /*Cuando se haga bien hacemos un diagrama para saber que tenemos que verifcar hacer y en el orden
        * */
        compraService.realizarCompra();
        entradaService.realizandoEntrada();
        pagoService.pasarelaDePago();
        facturaService.realizarFactura();
    }
}
