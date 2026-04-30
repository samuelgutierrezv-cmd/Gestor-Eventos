package org.samuel.gestor_eventos.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginControler {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void login(ActionEvent event)throws IOException{
        String email = emailField.getText().trim();
        String pass = passwordField.getText().trim();

        if( email == "user" && pass == "admin" ){
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/samuel/gestor_eventos/eventos.fxml")
            );

            Parent root = loader.load();
            root.resize(600, 550);

            Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventana.setScene(new Scene(root));
            ventana.show();
        }else{
            System.out.println("eroor");
        }

        System.out.println("Login: " + email + " - " + pass);
    }

    @FXML
    private void irRegistro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/samuel/gestor_eventos/registro-usuarios.fxml")
        );

        Parent root = loader.load();
        root.resize(600, 550);

        Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventana.setScene(new Scene(root));
        ventana.show();
    }
}
