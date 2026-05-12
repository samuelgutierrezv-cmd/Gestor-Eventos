package org.samuel.gestor_eventos.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginAdministrador {

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

    @FXML
    private void login() {

    }


    @FXML
    private void irRegistro(ActionEvent event) throws IOException {
        try {
            // Guardamos la escena actual ANTES de cambiar
            Scene escenaAnterior = ((Node) event.getSource()).getScene();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/registro-usuarios.fxml"));
            Parent root = loader.load();

            // Obtenemos el controlador de la nueva pantalla
            RegistroController pagoController = loader.getController();

            // Pasamos la escena anterior al controlador de Pago
            pagoController.setEscenaAnterior(escenaAnterior);

            // Cambiamos la escena
            Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventana.setScene(new Scene(root));
            ventana.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
