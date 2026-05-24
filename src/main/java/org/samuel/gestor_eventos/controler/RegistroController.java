package org.samuel.gestor_eventos.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import org.samuel.gestor_eventos.modelos.Usuario;

import java.util.ArrayList;

public class RegistroController {

    // ==================== CAMPOS FXML ====================

    @FXML
    private TextField nombreField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField telefonoField;

    // ==================== NAVEGACIÓN ====================

    private Scene escenaAnterior;

    @FXML
    private Button btnVolver;

    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    @FXML
    private void volver() {

        Stage stage = (Stage)
                btnVolver.getScene().getWindow();

        Navegacion.cambiarVentana(
                stage,
                "/org/samuel/gestor_eventos/login.fxml"
        );
    }

    // ==================== REGISTRO ====================

    @FXML
    public void registrar() {
        String nombre   = nombreField.getText().trim();
        String correo   = emailField.getText().trim();
        String password = passwordField.getText();
        String telefono = telefonoField.getText().trim();

        // 1. Campos vacíos
        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        // 2. Formato de correo básico
        if (!correo.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
            mostrarAlerta("El correo electrónico no tiene un formato válido.");
            return;
        }

        // 3. Contraseña mínimo 6 caracteres
        if (password.length() < 6) {
            mostrarAlerta("La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        // 4. Teléfono solo números y longitud correcta (10 dígitos Colombia)
        if (!telefono.matches("\\d{10}")) {
            mostrarAlerta("El teléfono debe contener exactamente 10 dígitos numéricos.");
            return;
        }

        // 5. Correo duplicado
        boolean correoExiste = RepositorioAdmin.getInstance().getUsuarios()
                .stream()
                .anyMatch(u -> u.getCorroElectronico().equalsIgnoreCase(correo));

        if (correoExiste) {
            mostrarAlerta("Ya existe una cuenta registrada con ese correo electrónico.");
            return;
        }

        // Todo OK → crear usuario
        int id = RepositorioAdmin.getInstance().getUsuarios().size() + 1;
        Usuario usuario = new Usuario(nombre, id, correo, telefono, new ArrayList<>(), password);
        RepositorioAdmin.getInstance().getUsuarios().add(usuario);

        Alert ok = new Alert(Alert.AlertType.INFORMATION);
        ok.setTitle("Registro exitoso");
        ok.setHeaderText(null);
        ok.setContentText("¡Bienvenido, " + nombre + "! Tu cuenta ha sido creada correctamente.");
        ok.showAndWait();

        // Ir al login
        Stage stage = (Stage) nombreField.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/login.fxml");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error de registro");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ==================== REGISTRO ADMIN ====================

    @FXML
    public void registroAdministrador(ActionEvent event) {
        try {
            Scene escenaAnterior =
                    ((Node) event.getSource())
                            .getScene();

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/org/samuel/gestor_eventos/registro-administradores.fxml"
                            )
                    );

            Parent root = loader.load();

            RegistroAdminisitradores controller = loader.getController();

            controller.setEscenaAnterior(escenaAnterior);

            Stage ventana =
                    (Stage) ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            ventana.setScene(new Scene(root));

            ventana.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}