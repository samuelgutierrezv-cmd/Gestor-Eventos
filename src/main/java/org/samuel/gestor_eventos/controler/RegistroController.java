package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegistroController {

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

    @FXML
    public void registrar(){

    }

    @FXML
    public void volverLogin(){

    }
}
