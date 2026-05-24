package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.interfaces.creacion.FactoryCompras;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryEventos;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryUsuarios;
import org.samuel.gestor_eventos.modelos.Administrador;

public class RegistroAdminisitradores {

    private Scene escenaAnterior;

    @FXML private Button btnVolver;
    @FXML private TextField nombreField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField telefonoField;

    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/login-Administrador.fxml");
    }

    @FXML
    public void registrar() {
        System.out.println("Entró al método registrar admin");
        String nombre   = nombreField.getText().trim();
        String correo   = emailField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String password = passwordField.getText();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        if (!correo.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
            mostrarAlerta("El correo electrónico no tiene un formato válido.");
            return;
        }

        if (password.length() < 6) {
            mostrarAlerta("La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            mostrarAlerta("El teléfono debe contener exactamente 10 dígitos numéricos.");
            return;
        }

        boolean correoExiste = RepositorioAdmin.getInstance().getAdministradores()
                .stream()
                .anyMatch(a -> a.getCorroElectronico().equalsIgnoreCase(correo));

        if (correoExiste) {
            mostrarAlerta("Ya existe un administrador registrado con ese correo.");
            return;
        }

        int id = RepositorioAdmin.getInstance().getAdministradores().size() + 1;

        Administrador admin = new Administrador(
                nombre, id, correo, telefono, password,
                new FactoryCompras(),
                new FactoryEventos(),
                new FactoryUsuarios()
        );

        RepositorioAdmin.getInstance().getAdministradores().add(admin);

        Alert ok = new Alert(Alert.AlertType.INFORMATION);
        ok.setTitle("Registro exitoso");
        ok.setHeaderText(null);
        ok.setContentText("Administrador " + nombre + " registrado correctamente.");
        ok.showAndWait();

        Stage stage = (Stage) btnVolver.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/login-Administrador.fxml");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error de registro");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}