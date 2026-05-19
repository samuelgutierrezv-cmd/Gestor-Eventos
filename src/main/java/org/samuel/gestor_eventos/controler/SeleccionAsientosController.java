package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.EstadoAsiento;
import org.samuel.gestor_eventos.modelos.Asiento;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Zona;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SeleccionAsientosController implements Initializable {

    @FXML private Label lblEvento;
    @FXML private Label lblDetalle;
    @FXML private Label lblRecinto;
    @FXML private ListView<Zona> listZonas;
    @FXML private FlowPane panelAsientos;

    private Evento eventoSeleccionado;
    private Zona zonaSeleccionada;
    private final ArrayList<Asiento> asientosSeleccionados = new ArrayList<>();
    private Scene escenaAnterior;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listZonas.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevaZona) -> {
            zonaSeleccionada = nuevaZona;
            cargarAsientos();
        });
    }

    public void setEventoSeleccionado(Evento evento) {

        this.eventoSeleccionado = evento;
        lblEvento.setText(evento.getNombre());
        lblDetalle.setText(
            evento.getCiudad()
            + " - "
            + evento.getFecha()
            + " - "
            + evento.getHora()
        );

        lblRecinto.setText(evento.getRecinto().getNombre());
        listZonas.getItems().setAll(evento.getRecinto().getConjuntoZonas());
    }

    private void cargarAsientos() {

        panelAsientos.getChildren().clear();

        if (zonaSeleccionada == null) {
            return;
        }

        for (Asiento asiento : zonaSeleccionada.getConfiguracionAsientos()) {

            Button btnAsiento = new Button(
            "F"
                + asiento.getFila()
                + " - "
                + asiento.getNumero()
            );

            btnAsiento.setPrefWidth(90);
            btnAsiento.setPrefHeight(45);

            if (asiento.getEstado() == EstadoAsiento.VENDIDO) {

                btnAsiento.setDisable(true);
                btnAsiento.setStyle("-fx-background-color: #ef4444;" + "-fx-text-fill: white;");

            } else if (asientosSeleccionados.contains(asiento)) {
                btnAsiento.setStyle("-fx-background-color: #2563eb;" + "-fx-text-fill: white;");
            } else {
                btnAsiento.setStyle("-fx-background-color: #22c55e;" + "-fx-text-fill: white;");
            }

            btnAsiento.setOnAction(e -> {

                if (asientosSeleccionados.contains(asiento)) {
                    asientosSeleccionados.remove(asiento);
                } else {
                    asientosSeleccionados.add(asiento);
                }

                cargarAsientos();
            });

            panelAsientos.getChildren().add(btnAsiento);
        }
    }

    private void limpiarSeleccionVisual() {
        cargarAsientos();
    }

    @FXML
    private void continuarPago() {

        if (zonaSeleccionada == null) {

            mostrarAlerta("Seleccione una zona");
            return;
        }

        if (asientosSeleccionados.isEmpty()) {

            mostrarAlerta("Seleccione un asiento");
            return;
        }

        for (Asiento asiento : asientosSeleccionados) {
            asiento.setEstado(EstadoAsiento.RESERVADO);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/pago.fxml"));

            Parent root = loader.load();
            PagoControler controller = loader.getController();

            controller.setEventoSeleccionado(eventoSeleccionado);
            controller.setZonaSeleccionada(zonaSeleccionada);
            controller.setAsientosSeleccionados(asientosSeleccionados);
            controller.setEscenaAnterior(panelAsientos.getScene());

            Stage stage = (Stage) panelAsientos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volver() {

        if (escenaAnterior != null) {
            Stage stage = (Stage) panelAsientos.getScene().getWindow();
            stage.setScene(escenaAnterior);
        }
    }

    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}