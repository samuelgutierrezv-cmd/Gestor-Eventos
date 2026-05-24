package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.modelos.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {

    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblTelefono;
    @FXML private ListView<String> listMetodosPago;
    @FXML private TextField txtNuevoMetodo;
    @FXML private Label lblMensaje;

    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            lblNombre.setText("Nombre: " + usuario.getNombre());
            lblCorreo.setText("Correo: " + usuario.getCorroElectronico());
            lblTelefono.setText("Teléfono: " + usuario.getNumeroTelefono());
            cargarMetodosPago();
        }
    }

    private void cargarMetodosPago() {
        if (usuario.getMetodosDePago() != null) {
            listMetodosPago.setItems(FXCollections.observableArrayList(usuario.getMetodosDePago()));
        }
    }

    @FXML
    private void agregarMetodo() {
        String nuevoMetodo = txtNuevoMetodo.getText().trim();
        if (nuevoMetodo.isEmpty()) {
            lblMensaje.setText("Ingrese un método de pago válido");
            return;
        }

        usuario.agregarMetodoPago(nuevoMetodo);
        cargarMetodosPago();
        txtNuevoMetodo.clear();
        lblMensaje.setText("Método de pago agregado: " + nuevoMetodo);
    }

    @FXML
    private void eliminarMetodo() {
        String seleccionado = listMetodosPago.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setText("Seleccione un método de pago para eliminar");
            return;
        }

        usuario.eliminarMetodoPago(seleccionado);
        cargarMetodosPago();
        lblMensaje.setText("Método de pago eliminado: " + seleccionado);
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/inicio.fxml");
    }
}