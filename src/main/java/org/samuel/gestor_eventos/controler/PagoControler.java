package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * PagoControler — controla la vista pago.fxml
 *
 * Responsabilidades:
 *  - Mostrar resumen del evento seleccionado
 *  - Gestionar cantidad de entradas (+/-)
 *  - Seleccionar forma de compra (en línea / físico / taquilla)
 *  - Seleccionar método de pago (tarjeta, PSE, Nequi, efectivo, etc.)
 *  - Calcular subtotal + cargo de servicio + total
 *  - Confirmar o cancelar la compra
 */
public class PagoControler implements Initializable {

    // ── Resumen evento ───────────────────────────────────────────────
    @FXML private Label lblEventoNombre;
    @FXML private Label lblEventoDetalle;
    @FXML private Label lblEventoRecinto;
    @FXML private Label lblPrecioEntrada;
    @FXML private Label lblZona;

    // ── Cantidad ─────────────────────────────────────────────────────
    @FXML private Label lblCantidad;
    @FXML private Label lblSubtotal;

    // ── Opciones de compra ───────────────────────────────────────────
    @FXML private VBox opcionEnLinea;
    @FXML private VBox opcionFisico;
    @FXML private VBox opcionTaquilla;
    @FXML private Label lblSelEnLinea;
    @FXML private Label lblSelFisico;
    @FXML private Label lblSelTaquilla;

    // ── Métodos de pago ──────────────────────────────────────────────
    @FXML private Label lblMetodoSeleccionado;

    // ── Resumen final ────────────────────────────────────────────────
    @FXML private Label lblResumenSubtotal;
    @FXML private Label lblResumenCargo;
    @FXML private Label lblResumenTotal;
    @FXML private Label lblTotalHeader;

    // ── Estado interno ───────────────────────────────────────────────
    private int    cantidad        = 1;
    private double precioUnitario  = 120_000;
    private double cargoServicio   = 0.10;   // 10 %
    private String metodoSeleccionado = "Tarjeta de Crédito";

    // ==================== VARIABLE DE NAVEGACIÓN ====================
    private Scene escenaAnterior;

    @FXML
    private Button btnVolver;

    // ==================== MÉTODO PARA RECIBIR ESCENA ANTERIOR ====================
    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    // ==================== MÉTODO VOLVER ====================
    @FXML
    private void volver() {
        if (escenaAnterior != null) {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(escenaAnterior);
        }
    }

    //

    private static final NumberFormat FMT =
            NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    // ────────────────────────────────────────────────────────────────
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // En producción recibirías el Evento seleccionado por parámetro
        // o mediante un servicio singleton. Aquí ponemos datos de ejemplo:
        lblEventoNombre.setText("Festival Estéreo Picnic");
        lblEventoDetalle.setText("📍 Bogotá  ·  📅 15 May 2025  ·  🕐 16:00");
        lblEventoRecinto.setText("🏟 Anfiteatro del Lago");
        lblPrecioEntrada.setText(formatear(precioUnitario));
        lblZona.setText("Zona: General");

        actualizarTotales();
    }

    // ── Cantidad ─────────────────────────────────────────────────────
    @FXML
    private void incrementar() {
        if (cantidad < 10) {
            cantidad++;
            actualizarTotales();
        }
    }

    @FXML
    private void decrementar() {
        if (cantidad > 1) {
            cantidad--;
            actualizarTotales();
        }
    }

    private void actualizarTotales() {
        double subtotal = precioUnitario * cantidad;
        double cargo    = subtotal * cargoServicio;
        double total    = subtotal + cargo;

        lblCantidad.setText(String.valueOf(cantidad));
        lblSubtotal.setText(formatear(subtotal));
        lblResumenSubtotal.setText(formatear(subtotal));
        lblResumenCargo.setText(formatear(cargo));
        lblResumenTotal.setText(formatear(total));
        lblTotalHeader.setText("Total: " + formatear(total));
    }

    // ── Forma de compra ──────────────────────────────────────────────
    @FXML
    private void seleccionarCompraEnLinea() {
        setOpcionActiva("linea");
    }

    @FXML
    private void seleccionarPuntoFisico() {
        setOpcionActiva("fisico");
    }

    @FXML
    private void seleccionarTaquilla() {
        setOpcionActiva("taquilla");
    }

    private void setOpcionActiva(String opcion) {
        // Limpiar todos
        lblSelEnLinea.setText("");
        lblSelFisico.setText("");
        lblSelTaquilla.setText("");

        switch (opcion) {
            case "linea"    -> lblSelEnLinea.setText("✔ Seleccionado");
            case "fisico"   -> lblSelFisico.setText("✔ Seleccionado");
            case "taquilla" -> lblSelTaquilla.setText("✔ Seleccionado");
        }
    }

    // ── Métodos de pago ──────────────────────────────────────────────
    @FXML private void seleccionarTarjetaCredito() { setMetodo("Tarjeta de Crédito"); }
    @FXML private void seleccionarTarjetaDebito()  { setMetodo("Tarjeta de Débito"); }
    @FXML private void seleccionarPSE()            { setMetodo("PSE"); }
    @FXML private void seleccionarNequi()          { setMetodo("Nequi"); }
    @FXML private void seleccionarEfectivo()       { setMetodo("Efectivo"); }
    @FXML private void seleccionarDaviplata()      { setMetodo("Daviplata"); }
    @FXML private void seleccionarQR()             { setMetodo("Código QR"); }
    @FXML private void seleccionarCuotas()         { setMetodo("Pago en cuotas"); }

    private void setMetodo(String nombre) {
        metodoSeleccionado = nombre;
        lblMetodoSeleccionado.setText(nombre);
    }

    // ── Acciones finales ─────────────────────────────────────────────
    @FXML
    private void confirmarCompra() {
        System.out.println("=== COMPRA CONFIRMADA ===");
        System.out.println("Evento   : " + lblEventoNombre.getText());
        System.out.println("Cantidad : " + cantidad);
        System.out.println("Método   : " + metodoSeleccionado);
        System.out.println("Total    : " + lblResumenTotal.getText());
        // TODO: llamar a CompraDAO.guardarCompra(...) y navegar a confirmación
    }

    @FXML
    private void cancelar() {
        System.out.println("Compra cancelada — volver a eventos");
        // TODO: navegar de vuelta a la lista de eventos
    }

    // ── Utilidad ─────────────────────────────────────────────────────
    private String formatear(double valor) {
        return FMT.format(valor);
    }
}
