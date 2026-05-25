package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.modelos.Compra;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class GestionComprasController implements Initializable {

    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableColumn<Compra, Integer> colId;
    @FXML private TableColumn<Compra, String> colUsuario;
    @FXML private TableColumn<Compra, String> colEvento;
    @FXML private TableColumn<Compra, LocalDate> colFecha;
    @FXML private TableColumn<Compra, Double> colTotal;
    @FXML private TableColumn<Compra, EstadoCompras> colEstado;

    @FXML private ComboBox<String> filtroEstado;
    @FXML private DatePicker filtroFecha;
    @FXML private Label lblTotalCompras;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsuario.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getUsuarioAsociado().getNombre()
                )
        );
        colEvento.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getEventoAsociado().getNombre()
                )
        );
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarCompras();

        filtroEstado.getItems().addAll("Todos", "CREADA", "PAGADA", "CONFIRMADA", "CANCELADA", "REEMBOLSADA", "INCIDENCIA");
        filtroEstado.setValue("Todos");
    }

    private void cargarCompras() {
        var compras = FXCollections.observableArrayList(
                RepositorioAdmin.getInstance().getCompras()
        );
        tablaCompras.setItems(compras);
        actualizarContador();
    }

    @FXML
    private void filtrar() {
        String estado = filtroEstado.getValue();
        LocalDate fecha = filtroFecha.getValue();

        var compras = FXCollections.observableArrayList(
                RepositorioAdmin.getInstance().getCompras().stream()
                        .filter(c -> {
                            boolean okEstado = estado == null || estado.equals("Todos")
                                    || c.getEstado().name().equals(estado);
                            boolean okFecha = fecha == null
                                    || c.getFechaCompra().toLocalDate().equals(fecha);
                            return okEstado && okFecha;
                        })
                        .toList()
        );
        tablaCompras.setItems(compras);
        actualizarContador();
    }

    private void actualizarContador() {
        lblTotalCompras.setText("Total compras: " + tablaCompras.getItems().size());
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

        if (seleccionada.getEstado() == EstadoCompras.CANCELADA ||
                seleccionada.getEstado() == EstadoCompras.REEMBOLSADA) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("La compra ya está cancelada o reembolsada");
            alert.show();
            return;
        }

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Cancelar Compra");
        confirmar.setHeaderText("¿Está seguro de cancelar la compra #" + seleccionada.getId() + "?");
        confirmar.setContentText("Esta acción no se puede deshacer.");
        var respuesta = confirmar.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            seleccionada.setEstado(EstadoCompras.CANCELADA);
            seleccionada.getEntradas().forEach(e -> e.getAsiento().setEstado(EstadoAsiento.DISPONIBLE));
            cargarCompras();
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText(null);
            ok.setContentText("Compra cancelada exitosamente");
            ok.show();
        }
    }

    @FXML
    private void reembolsarCompra() {
        Compra seleccionada = tablaCompras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Seleccione una compra");
            alert.show();
            return;
        }

        if (seleccionada.getEstado() != EstadoCompras.PAGADA &&
                seleccionada.getEstado() != EstadoCompras.CONFIRMADA) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Solo se pueden reembolsar compras pagadas o confirmadas");
            alert.show();
            return;
        }

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Reembolsar Compra");
        confirmar.setHeaderText("¿Está seguro de reembolsar la compra #" + seleccionada.getId() + "?");
        confirmar.setContentText("Se devolverá el dinero al usuario.");
        var respuesta = confirmar.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            seleccionada.setEstado(EstadoCompras.REEMBOLSADA);
            seleccionada.getEntradas().forEach(e -> e.getAsiento().setEstado(EstadoAsiento.DISPONIBLE));
            cargarCompras();
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText(null);
            ok.setContentText("Compra reembolsada exitosamente");
            ok.show();
        }
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) tablaCompras.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/administrador.fxml");
    }
}