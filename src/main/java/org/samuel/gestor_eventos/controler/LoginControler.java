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
import org.samuel.gestor_eventos.modelos.Usuario;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.ArrayList;

public class LoginControler {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void login(ActionEvent event)throws IOException{
        String email = emailField.getText().trim();
        String pass = passwordField.getText();
        boolean todoCorrecto = false;
        ArrayList<Usuario> usuarios = new ArrayList<>();

        for (Usuario b: usuarios){
            if( b.getCorroElectronico().equalsIgnoreCase(email) && b.getPassword().equals(pass)){
                todoCorrecto = true;
                break;
            }
        }

        if( email.equalsIgnoreCase("u") && pass.equalsIgnoreCase("a")){
            try {
                // Guardamos la escena actual ANTES de cambiar
                Scene escenaAnterior = ((Node) event.getSource()).getScene();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/eventos.fxml"));
                Parent root = loader.load();

                // Obtenemos el controlador de la nueva pantalla
                EventosControler pagoController = loader.getController();

                // Pasamos la escena anterior al controlador de Pago
                pagoController.setEscenaAnterior(escenaAnterior);

                // Cambiamos la escena
                Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();
                ventana.setScene(new Scene(root));
                ventana.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(todoCorrecto ){
            try {
                // Guardamos la escena actual ANTES de cambiar
                Scene escenaAnterior = ((Node) event.getSource()).getScene();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/eventos.fxml"));
                Parent root = loader.load();

                // Obtenemos el controlador de la nueva pantalla
                EventosControler pagoController = loader.getController();

                // Pasamos la escena anterior al controlador de Pago
                pagoController.setEscenaAnterior(escenaAnterior);

                // Cambiamos la escena
                Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();
                ventana.setScene(new Scene(root));
                ventana.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            System.out.println("El usuario no se esncuentra en la base de datos ni es usuario admin" );
        }

        System.out.println("Login: " + email + " - " + pass);
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
