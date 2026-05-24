package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.scene.Scene;
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

    // ==================== MÉTODO PARA RECIBIR ESCENA ANTERIOR ====================

    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    // ==================== MÉTODO VOLVER ====================

    @FXML
    private void volver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/login-Administrador.fxml");
    }

    // ==================== REGISTRO ====================

    @FXML
    public void registrar() {
        try {
            String nombre = nombreField.getText();
            String correo = emailField.getText();
            String telefono = telefonoField.getText();
            String password = passwordField.getText();
            int id =
                    RepositorioAdmin.getInstance()
                            .getAdministradores()
                            .size() + 1;

            Administrador admin =
                    new Administrador(
                            nombre,
                            id,
                            correo,
                            telefono,
                            password,
                            new FactoryCompras(),
                            new FactoryEventos(),
                            new FactoryUsuarios()
                    );

            RepositorioAdmin.getInstance().getAdministradores().add(admin);

            System.out.println("Administrador registrado correctamente");
            System.out.println(
                "Admins registrados: "
                        + RepositorioAdmin.getInstance()
                        .getAdministradores()
                        .size()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}