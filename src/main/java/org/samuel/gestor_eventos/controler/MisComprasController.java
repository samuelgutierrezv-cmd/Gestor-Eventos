package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Facturas;
import org.samuel.gestor_eventos.modelos.Usuario;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MisComprasController implements Initializable {

    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, Integer> colId;
    @FXML private TableColumn<Compra, String> colEvento;
    @FXML private TableColumn<Compra, LocalDate> colFecha;
    @FXML private TableColumn<Compra, Double> colTotal;
    @FXML private TableColumn<Compra, EstadoCompras> colEstado;

    @FXML private ComboBox<String> filtroEstado;
    @FXML private DatePicker filtroFecha;
    @FXML private Label lblTotalCompras;

    private FilteredList<Compra> comprasFiltradas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEvento.setCellValueFactory(new PropertyValueFactory<>("eventoAsociado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        var compras = FXCollections.observableArrayList(
                RepositorioAdmin.getInstance().getCompras().stream()
                        .filter(c -> c.getUsuarioAsociado().getDi() == Sesion.getUsuarioActual().getDi())
                        .toList()
        );

        comprasFiltradas = new FilteredList<>(compras, c -> true);
        tablaCompras.setItems(comprasFiltradas);
        actualizarContador();

        filtroEstado.getItems().addAll("Todos", "CREADA", "PAGADA", "CONFIRMADA", "CANCELADA", "REEMBOLSADA", "INCIDENCIA");
        filtroEstado.setValue("Todos");
    }

    @FXML
    private void filtrar() {
        String estado = filtroEstado.getValue();
        LocalDate fecha = filtroFecha.getValue();

        comprasFiltradas.setPredicate(compra -> {
            boolean okEstado = estado == null || estado.equals("Todos")
                    || compra.getEstado().name().equals(estado);
            boolean okFecha = fecha == null
                    || compra.getFechaCompra().toLocalDate().equals(fecha);
            return okEstado && okFecha;
        });
        actualizarContador();
    }

    private void actualizarContador() {
        lblTotalCompras.setText("Total compras: " + comprasFiltradas.size());
    }

    @FXML
    private void verDetalle() {
        Compra seleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione una compra");
            alert.show();
            return;
        }

        Alert detalles = new Alert(Alert.AlertType.INFORMATION);
        detalles.setTitle("Detalle de Compra #" + seleccionada.getId());
        detalles.setHeaderText("Evento: " + seleccionada.getEventoAsociado().getNombre());
        StringBuilder sb = new StringBuilder();
        sb.append("Fecha: ").append(seleccionada.getFechaCompra()).append("\n");
        sb.append("Total: $").append(seleccionada.getValor()).append("\n");
        sb.append("Estado: ").append(seleccionada.getEstado()).append("\n");
        sb.append("Entradas: ").append(seleccionada.getEntradas().size()).append("\n");
        sb.append("Servicios: ").append(seleccionada.getServiciosAdicionales());
        detalles.setContentText(sb.toString());
        detalles.show();
    }

    @FXML
    private void cancelarCompra() {
        Compra seleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione una compra");
            alert.show();
            return;
        }

        if (seleccionada.getEstado() != EstadoCompras.CREADA &&
                seleccionada.getEstado() != EstadoCompras.PAGADA) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Solo se pueden cancelar compras en estado CREADA o PAGADA");
            alert.show();
            return;
        }

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Cancelar Compra");
        confirmar.setHeaderText("¿Está seguro de cancelar la compra #" + seleccionada.getId() + "?");
        confirmar.setContentText("Se reembolsará el valor de la compra.");
        var respuesta = confirmar.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            seleccionada.setEstado(EstadoCompras.CANCELADA);
            seleccionada.getEntradas().forEach(e -> e.getAsiento().setEstado(EstadoAsiento.DISPONIBLE));
            tablaCompras.refresh();
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText(null);
            ok.setContentText("Compra cancelada exitosamente");
            ok.show();
        }
    }

    @FXML
    private void verNotificaciones() {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario == null || usuario.getNotificaciones().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No tienes notificaciones pendientes");
            alert.show();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String notif : usuario.getNotificaciones()) {
            sb.append("• ").append(notif).append("\n\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notificaciones");
        alert.setHeaderText("Tienes " + usuario.getNotificaciones().size() + " notificaciones");
        alert.setContentText(sb.toString());
        alert.show();

        usuario.limpiarNotificaciones();
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) tablaCompras.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/inicio.fxml");
    }

    @FXML
    private void descargarFactura() {
        Compra seleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione una compra");
            alert.show();
            return;
        }

        // Buscar la factura asociada a la compra
        Facturas factura = null;
        for (Facturas f : RepositorioAdmin.getInstance().getFacturas()) {
            if (f.getCompra().getId() == seleccionada.getId()) {
                factura = f;
                break;
            }
        }

        if (factura == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No se encontró factura para esta compra");
            alert.show();
            return;
        }

        // Abrir la ventana de factura
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/factura.fxml"));
            Parent root = loader.load();
            FacturaController controller = loader.getController();
            controller.setFactura(factura);
            controller.setEscenaAnterior(tablaCompras.getScene());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Factura #" + factura.getIdFactura());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error al abrir la factura: " + e.getMessage());
            alert.show();
        }
    }
}