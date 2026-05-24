package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.samuel.gestor_eventos.modelos.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {

    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblTelefono;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            lblNombre.setText("Nombre: " + usuario.getNombre());
            lblCorreo.setText("Correo: " + usuario.getCorroElectronico());
            lblTelefono.setText("Teléfono: " + usuario.getNumeroTelefono());
        }
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/inicio.fxml");
    }
}