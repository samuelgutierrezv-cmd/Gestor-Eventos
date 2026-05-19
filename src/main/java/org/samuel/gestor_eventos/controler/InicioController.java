package org.samuel.gestor_eventos.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InicioController {

    @FXML
    private void abrirEventos(ActionEvent event) {
        cambiarVentana(
                event,
                "/org/samuel/gestor_eventos/eventos.fxml"
        );
    }

    @FXML
    private void abrirCompras(ActionEvent event) {

        cambiarVentana(
                event,
                "/org/samuel/gestor_eventos/pago.fxml"
        );
    }

    @FXML
    private void abrirFacturas(ActionEvent event) {

        cambiarVentana(
                event,
                "/org/samuel/gestor_eventos/factura.fxml"
        );
    }

    @FXML
    private void abrirIncidencias(ActionEvent event) {

        cambiarVentana(
                event,
                "/org/samuel/gestor_eventos/incidencias.fxml"
        );
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        cambiarVentana(
                event,
                "/org/samuel/gestor_eventos/login.fxml"
        );
    }

    private void cambiarVentana(ActionEvent event, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(rutaFXML)
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node)
                    event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}