package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Zona;
import javafx.event.ActionEvent;

public class DetalleEventoController {

    @FXML private Label nombreLabel;
    @FXML private Label categoriaLabel;
    @FXML private Label fechaLabel;
    @FXML private Label ciudadLabel;
    @FXML private Label estadoLabel;
    @FXML private Label descripcionLabel;
    @FXML private Label recintoLabel;
    @FXML private ListView<Zona> zonasListView;

    private Evento evento;
    private Evento eventoSeleccionado;
    private Scene escenaAnterior;

    public void setEvento(Evento evento) {
        this.evento = evento;
        cargarInformacion();
    }

    public void setEventoSeleccionado(Evento evento) {

        this.eventoSeleccionado = evento;

        nombreLabel.setText(evento.getNombre());
        categoriaLabel.setText("Categoría: " + evento.getCategoria());
        fechaLabel.setText("Fecha: " + evento.getFecha() + " - " + evento.getHora());
        ciudadLabel.setText("Ciudad: " + evento.getCiudad());
        estadoLabel.setText("Estado: " + evento.getEstado());
        descripcionLabel.setText(evento.getDescripcion());
        recintoLabel.setText(evento.getRecinto().getNombre());
        zonasListView.getItems().setAll(evento.getRecinto().getConjuntoZonas());
    }

    public void setEscenaAnterior(Scene escena) {

        this.escenaAnterior = escena;
    }

    private void cargarInformacion() {

        nombreLabel.setText(evento.getNombre());
        categoriaLabel.setText("Categoría: " + evento.getCategoria().name());
        fechaLabel.setText("Fecha: " + evento.getFecha());
        ciudadLabel.setText("Ciudad: " + evento.getCiudad());
        estadoLabel.setText("Estado: " + evento.getEstado().name());
        descripcionLabel.setText(evento.getDescripcion());
        recintoLabel.setText(evento.getRecinto().getNombre());

        zonasListView.setItems(
                FXCollections.observableArrayList(
                        evento.getRecinto().getConjuntoZonas()
                )
        );
    }

    @FXML
    private void comprarEntradas(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/org/samuel/gestor_eventos/seleccion-asientos.fxml"
                    )
            );

            Parent root = loader.load();
            SeleccionAsientosController controller = loader.getController();

            controller.setEventoSeleccionado(eventoSeleccionado);
            controller.setEscenaAnterior(((Node) event.getSource()).getScene());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volver() {
        if (escenaAnterior != null) {
            Stage stage = (Stage) nombreLabel.getScene().getWindow();
            stage.setScene(escenaAnterior);
        }
    }
}