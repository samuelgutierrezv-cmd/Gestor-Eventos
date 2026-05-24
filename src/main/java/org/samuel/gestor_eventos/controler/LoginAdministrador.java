package org.samuel.gestor_eventos.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.samuel.gestor_eventos.modelos.Administrador;

public class LoginAdministrador {

    private Scene escenaAnterior;

    @FXML private Button btnVolver;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button btnIngresar;

    // ==================== MÉTODO PARA RECIBIR ESCENA ANTERIOR ====================

    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    // ==================== MÉTODO VOLVER ====================

    @FXML
    private void volver() {

        Stage stage = (Stage) btnVolver.getScene().getWindow();

        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/login.fxml");
    }

    // ==================== LOGIN ADMIN ====================

    @FXML
    private void login(ActionEvent event) {
        String correo = emailField.getText();
        String password = passwordField.getText();

        for (Administrador admin : RepositorioAdmin.getInstance().getAdministradores()) {
            if (admin.getCorroElectronico().equals(correo) && admin.getPassword().equals(password)) {
                System.out.println("Bienvenido administrador " + admin.getNombre());
                abrirPanelAdmin(event);
                return;
            }
        }
        System.out.println("Administrador no encontrado");
    }

    // ==================== IR A REGISTRO ====================

    @FXML
    private void irRegistro(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/login-Administrador.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== ABRIR PANEL ADMIN ====================

    private void abrirPanelAdmin(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/org/samuel/gestor_eventos/administrador.fxml"
                    )
            );

            Parent root = loader.load();

            AdminControler controller = loader.getController();

            Scene escenaActual =
                    ((Node) event.getSource())
                            .getScene();

            controller.setEscenaAnterior(escenaActual);

            Stage stage =
                    (Stage) ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}