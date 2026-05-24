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
import javafx.scene.control.Alert;

public class LoginControler {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void login(ActionEvent event) {
        String correo   = emailField.getText().trim();
        String password = passwordField.getText();

        if (correo.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Por favor completa todos los campos.");
            return;
        }

        Usuario usuarioEncontrado = null;
        for (Usuario u : RepositorioAdmin.getInstance().getUsuarios()) {
            if (u.getCorroElectronico().equals(correo) && u.getPassword().equals(password)) {
                usuarioEncontrado = u;
                break;
            }
        }

        if (usuarioEncontrado == null) {
            mostrarAlerta("Correo o contraseña incorrectos.");
            return;
        }

        Sesion.setUsuarioActual(usuarioEncontrado);
        abrirVentana(event, "/org/samuel/gestor_eventos/inicio.fxml");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de inicio de sesión");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void irRegistro(ActionEvent event) {

        Stage stage = (Stage)
                ((Node) event.getSource())
                        .getScene()
                        .getWindow();

        Navegacion.cambiarVentana(
                stage,
                "/org/samuel/gestor_eventos/registro-usuarios.fxml"
        );
    }

    @FXML
    private void loginAdministrador(ActionEvent event) {

        abrirVentana(
                event,
                "/org/samuel/gestor_eventos/registro-administradores.fxml"
        );
    }

    private void abrirVentana(ActionEvent event, String ruta){

        try{

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(ruta)
            );

            Parent root = loader.load();

            Stage stage = (Stage)
                    ((javafx.scene.Node)
                            event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));

            stage.show();

        }catch (Exception e){

            e.printStackTrace();
        }
    }
}