package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;

import org.samuel.gestor_eventos.modelos.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class TablaUsuariosController implements Initializable {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, String> colTelefono;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("di"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("corroElectronico"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("numeroTelefono"));

        tablaUsuarios.setItems(FXCollections.observableArrayList(
            RepositorioAdmin
                    .getInstance()
                    .getUsuarios()
        ));
    }
}