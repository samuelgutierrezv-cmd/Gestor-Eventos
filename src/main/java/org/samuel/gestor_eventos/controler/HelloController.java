package org.samuel.gestor_eventos.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/samuel/gestor_eventos/vista.fxml")
        );

        Parent root = loader.load();
        root.resize(700, 700);

        Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventana.setScene(new Scene(root));
        ventana.show();
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
