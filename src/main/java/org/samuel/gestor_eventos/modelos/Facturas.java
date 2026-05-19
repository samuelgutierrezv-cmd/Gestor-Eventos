package org.samuel.gestor_eventos.modelos;

import org.samuel.gestor_eventos.interfaces.creacion.Pasarela;

public class Facturas implements Pasarela {

    private int idFactura;
    private Compra compra;
    private Pago pago;

    public Facturas(int idFactura, Compra compra, Pago pago) {
        this.idFactura = idFactura;
        this.compra = compra;
        this.pago = pago;
    }

    public String generarFactura() {
        return """
            ===== FACTURA =====
            ID Factura: %d
            Cliente: %s
            Evento: %s
            Total Pagado: %.2f
            Metodo Pago: %s
            Estado Compra: %s
            ===================
            """.formatted(
                idFactura,
                compra.getUsuarioAsociado().getNombre(),
                compra.getEventoAsociado().getNombre(),
                pago.getMonto(),
                pago.getMetodoPago(),
                compra.getEstado()
        );
    }

    public String generarCSV() {
        return """
            ID_FACTURA,CLIENTE,EVENTO,TOTAL,METODO_PAGO,ESTADO
            %d,%s,%s,%.2f,%s,%s
            """.formatted(
                idFactura,
                compra.getUsuarioAsociado().getNombre(),
                compra.getEventoAsociado().getNombre(),
                pago.getMonto(),
                pago.getMetodoPago(),
                compra.getEstado()
        );
    }

    public void consultarFactura() {
        System.out.println(generarFactura());
    }

    public String facturaCCV() {
        return idFactura + "," +
                compra.getUsuarioAsociado().getNombre() + "," +
                compra.getEventoAsociado().getNombre() + "," +
                pago.getMonto() + "," +
                pago.getMetodoPago();
    }

    public void facturaPDF() {
        System.out.println("===== REPORTE PDF =====");
        System.out.println(generarFactura());
    }

    @Override
    public boolean guardar(Pasarela pasarela) {
        return pasarela != null;
    }

    @Override
    public Pasarela buscar(int id) {

        if (this.idFactura == id) {
            return this;
        }

        return null;
    }

    @Override
    public boolean actualizar(Pasarela pasarela) {

        if (pasarela instanceof Facturas f) {

            this.compra = f.compra;
            this.pago = f.pago;

            return true;
        }

        return false;
    }

    @Override
    public boolean eliminar(int id) {

        if (this.idFactura == id) {

            this.compra = null;
            this.pago = null;

            return true;
        }

        return false;
    }

    // ==================== GETTERS ====================

    public int getIdFactura() {
        return idFactura;
    }

    public Compra getCompra() {
        return compra;
    }

    public Pago getPago() {
        return pago;
    }

    // ==================== SETTERS ====================

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}