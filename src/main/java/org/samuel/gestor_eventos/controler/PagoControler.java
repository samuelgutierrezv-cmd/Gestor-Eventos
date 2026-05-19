package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.enums.EstadoEntrada;
import org.samuel.gestor_eventos.enums.EstadoPago;

import org.samuel.gestor_eventos.interfaces.comportamiento.PagoBancolombia;
import org.samuel.gestor_eventos.interfaces.comportamiento.PagoNequi;
import org.samuel.gestor_eventos.interfaces.comportamiento.PagoTarjeta;

import org.samuel.gestor_eventos.interfaces.creacion.FactoryCompras;
import org.samuel.gestor_eventos.interfaces.estructura.CompraFacade;

import org.samuel.gestor_eventos.modelos.Asiento;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Entrada;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Facturas;
import org.samuel.gestor_eventos.modelos.Pago;
import org.samuel.gestor_eventos.controler.Sesion;
import org.samuel.gestor_eventos.modelos.Zona;

import java.net.URL;

import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class PagoControler implements Initializable {

    @FXML private Label lblEventoNombre;
    @FXML private Label lblEventoDetalle;
    @FXML private Label lblEventoRecinto;
    @FXML private Label lblPrecioEntrada;
    @FXML private Label lblZona;
    @FXML private Label lblCantidad;
    @FXML private Label lblSubtotal;
    @FXML private Label lblMetodoSeleccionado;
    @FXML private Label lblResumenSubtotal;
    @FXML private Label lblResumenCargo;
    @FXML private Label lblResumenTotal;
    @FXML private Button btnVolver;

    private int cantidad = 1;
    private double precioUnitario = 120000;
    private double cargoServicio = 0.10;
    private String metodoSeleccionado = "Tarjeta de Crédito";
    private Scene escenaAnterior;
    private Evento eventoSeleccionado;
    private Zona zonaSeleccionada;
    private ArrayList<Asiento> asientosSeleccionados = new ArrayList<>();;

    private static final NumberFormat FMT = NumberFormat.getCurrencyInstance(
        new Locale("es", "CO")
    );

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblPrecioEntrada.setText(formatear(precioUnitario));

        if (lblZona != null) {
            lblZona.setText("Zona");
        }
        actualizarTotales();
    }

    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    @FXML
    private void volver() {
        if (escenaAnterior != null) {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(escenaAnterior);
        }
    }

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
        double cargo = subtotal * cargoServicio;
        double total = subtotal + cargo;

        lblCantidad.setText(String.valueOf(cantidad));
        lblSubtotal.setText("Subtotal: " + formatear(subtotal));
        lblResumenSubtotal.setText("Subtotal final: " + formatear(subtotal));
        lblResumenCargo.setText("Cargo servicio: " + formatear(cargo));
        lblResumenTotal.setText("Total: " + formatear(total));
    }

    @FXML
    private void seleccionarTarjetaCredito() {
        setMetodo("Tarjeta de Crédito");
    }

    @FXML
    private void seleccionarNequi() {
        setMetodo("Nequi");
    }

    @FXML
    private void seleccionarDaviplata() {
        setMetodo("Daviplata");
    }

    private void setMetodo(String nombre) {
        metodoSeleccionado = nombre;
        lblMetodoSeleccionado.setText(nombre);
    }

    @FXML
    private void confirmarCompra() {
        double subtotal = precioUnitario * cantidad;
        double cargo = subtotal * cargoServicio;
        double total = subtotal + cargo;

        Pago pago = new Pago(
            1,
            total,
            new java.sql.Date(System.currentTimeMillis()),
            EstadoPago.PENDIENTE,
            metodoSeleccionado
        );

        RepositorioAdmin.getInstance().getPagos().add(pago);

        switch (metodoSeleccionado) {
            case "Nequi":
                pago.setStrategy(new PagoNequi());
                break;
            case "Daviplata":
                pago.setStrategy(new PagoBancolombia());
                break;
            default:
                pago.setStrategy(new PagoTarjeta());
                break;
        }

        boolean aprobado = pago.procesarPago();

        FactoryCompras factoryCompras = new FactoryCompras();

        ArrayList<Entrada> entradas = new ArrayList<>();

        for (Asiento asiento : asientosSeleccionados) {

            Entrada entrada = new Entrada(
                entradas.size() + 1,
                zonaSeleccionada,
                asiento,
                precioUnitario,
                EstadoEntrada.ACTIVA
            );

            entradas.add(entrada);
        }

        Compra compra = (Compra) factoryCompras.creacionCompra(
            1,
            Sesion.getUsuarioActual(),
            (float) total,
            eventoSeleccionado,
            new java.sql.Date(System.currentTimeMillis()),
            EstadoCompras.CREADA,
            new ArrayList<>(),
            new ArrayList<>(),
            entradas
        );

        RepositorioAdmin.getInstance().getCompras().add(compra);

        if (aprobado) {
            compra.setEstado(EstadoCompras.PAGADA);
        } else {
            compra.setEstado(EstadoCompras.CANCELADA);
        }

        CompraFacade facade = new CompraFacade(pago.getStrategy());

        boolean resultado = facade.realizarCompraCompleta(compra);

        if (resultado) {
            for (Asiento asiento : asientosSeleccionados) {
                asiento.setEstado(EstadoAsiento.VENDIDO);
            }

            Facturas factura = new Facturas(
        RepositorioAdmin
                .getInstance()
                .getFacturas()
                .size() + 1,
                compra,
                pago
            );

            RepositorioAdmin.getInstance().getFacturas().add(factura);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/factura.fxml"));
                Parent root = loader.load();
                FacturaController controller = loader.getController();

                controller.setFactura(factura);
                controller.setEscenaAnterior(btnVolver.getScene());

                Stage stage = (Stage) btnVolver.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error en la compra");
        }
    }

    @FXML
    private void cancelar() {
        if (!asientosSeleccionados.isEmpty()) {
            for (Asiento asiento : asientosSeleccionados) {
                asiento.setEstado(EstadoAsiento.DISPONIBLE);
            }
        }
        System.out.println("Compra cancelada");
    }

    private String formatear(double valor) {
        return FMT.format(valor);
    }

    public void setEventoSeleccionado(Evento evento) {
        this.eventoSeleccionado = evento;

        lblEventoNombre.setText(evento.getNombre());
        lblEventoDetalle.setText(
            "📍 "
            + evento.getCiudad()
            + " · 📅 "
            + evento.getFecha()
            + " · 🕐 "
            + evento.getHora()
        );

        lblEventoRecinto.setText("🏟 " + evento.getRecinto().getNombre());
    }

    public void setAsientosSeleccionados(ArrayList<Asiento> asientos) {
        this.asientosSeleccionados = asientos;
        cantidad = asientos.size();
        actualizarTotales();
    }

    public void setZonaSeleccionada(Zona zona) {
        this.zonaSeleccionada = zona;
        precioUnitario = zona.getPrecioBase();
        lblZona.setText("Zona: " + zona.getNombre());
        lblPrecioEntrada.setText(formatear(precioUnitario));
        actualizarTotales();
    }
}