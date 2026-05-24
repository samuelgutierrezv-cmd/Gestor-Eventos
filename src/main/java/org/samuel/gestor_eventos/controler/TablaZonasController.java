package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import org.samuel.gestor_eventos.modelos.Zona;

import java.net.URL;
import java.util.ResourceBundle;

public class TablaZonasController implements Initializable {

    @FXML private TableView<Zona> tablaZonas;
    @FXML private TableColumn<Zona, Integer> colId;
    @FXML private TableColumn<Zona, String> colNombre;
    @FXML private TableColumn<Zona, String> colSector;
    @FXML private TableColumn<Zona, Integer> colCapacidad;
    @FXML private TableColumn<Zona, Double> colPrecio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colSector.setCellValueFactory(new PropertyValueFactory<>("sector"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioBase"));

        tablaZonas.setItems(FXCollections.observableArrayList(
                RepositorioAdmin.getInstance().getZonas()
        ));
    }
}