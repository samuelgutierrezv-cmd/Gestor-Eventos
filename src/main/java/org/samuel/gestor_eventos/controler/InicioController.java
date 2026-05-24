package org.samuel.gestor_eventos.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.samuel.gestor_eventos.modelos.Usuario;

public class InicioController {

    @FXML
    private void abrirEventos(ActionEvent event) {
        mostrarNotificaciones();
        cambiarVentana(event, "/org/samuel/gestor_eventos/eventos.fxml");
    }

    @FXML
    private void abrirCompras(ActionEvent event) {
        mostrarNotificaciones();
        cambiarVentana(event, "/org/samuel/gestor_eventos/mis-compras.fxml");
    }

    private void mostrarNotificaciones() {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario == null || usuario.getNotificaciones().isEmpty()) {
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
    private void abrirFacturas(ActionEvent event) {
        cambiarVentana(event, "/org/samuel/gestor_eventos/factura.fxml");
    }

    @FXML
    private void abrirPerfil(ActionEvent event) {
        cambiarVentana(event, "/org/samuel/gestor_eventos/perfil.fxml");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        Sesion.cerrarSesion();
        cambiarVentana(event, "/org/samuel/gestor_eventos/login.fxml");
    }

    private void cambiarVentana(ActionEvent event, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}