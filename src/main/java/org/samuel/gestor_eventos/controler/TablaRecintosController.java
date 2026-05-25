package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleIntegerProperty;

import org.samuel.gestor_eventos.modelos.Recinto;

import java.net.URL;
import java.util.ResourceBundle;

public class TablaRecintosController implements Initializable {

    @FXML private TableView<Recinto> tablaRecintos;
    @FXML private TableColumn<Recinto, Integer> colId;
    @FXML private TableColumn<Recinto, String> colNombre;
    @FXML private TableColumn<Recinto, String> colCiudad;
    @FXML private TableColumn<Recinto, String> colDireccion;
    @FXML private TableColumn<Recinto, Integer> colZonas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        // ✅ CORRECCIÓN: Usar SimpleIntegerProperty
        colZonas.setCellValueFactory(cellData -> {
            Recinto recinto = cellData.getValue();
            return new SimpleIntegerProperty(recinto.getConjuntoZonas().size()).asObject();
        });

        tablaRecintos.setItems(FXCollections.observableArrayList(
                RepositorioAdmin.getInstance().getRecintos()
        ));
    }
}