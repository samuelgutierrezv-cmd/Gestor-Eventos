package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Usuario;
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
        zonasListView.setCellFactory(lv -> new ListCell<Zona>() {
            @Override
            protected void updateItem(Zona zona, boolean empty) {
                super.updateItem(zona, empty);
                if (empty || zona == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(String.format("%s - $%.2f - Capacidad: %d",
                            zona.getNombre(), zona.getPrecioBase(), zona.getCapacidad()));
                }
            }
        });
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
        if (eventoSeleccionado.getEstado() != EstadoEvento.PUBLICADO && eventoSeleccionado.getEstado() != EstadoEvento.ACTIVO) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("El evento no está disponible");
            alert.showAndWait();
            return;
        }

        if (!eventoSeleccionado.hayDisponibilidad()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("No hay asientos disponibles");
            alert.showAndWait();
            return;
        }

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
    private void cancelarEventoTest() {

        eventoSeleccionado.cancelar();
    }

    @FXML
    private void verNotificaciones() {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario == null || usuario.getNotificaciones().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No tienes notificaciones pendientes");
            alert.show();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String notif : usuario.getNotificaciones()) {
            sb.append("• ").append(notif).append("\n\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notificaciones");
        alert.setHeaderText("Tienes " + usuario.getNotificaciones().size() + " notificaciones");
        alert.setContentText(sb.toString());
        alert.show();

        usuario.limpiarNotificaciones();
    }

    @FXML
    private void volver() {
        if (escenaAnterior != null) {
            Stage stage = (Stage) nombreLabel.getScene().getWindow();
            stage.setScene(escenaAnterior);
        }
    }
}