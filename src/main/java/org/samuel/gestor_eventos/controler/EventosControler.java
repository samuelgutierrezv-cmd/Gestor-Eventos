package org.samuel.gestor_eventos.controler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.samuel.gestor_eventos.modelos.Zona;
import org.samuel.gestor_eventos.enums.Sector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.samuel.gestor_eventos.enums.CategoriaEvento;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Recinto;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;

public class EventosControler implements Initializable {

    @FXML private ListView<Evento> eventosListView;
    @FXML private Label contadorLabel;
    @FXML private TextField filtroCiudad;
    @FXML private ComboBox<String> filtroCategoria;
    @FXML private ComboBox<String> filtroEstado;
    private ObservableList<Evento> todosLosEventos;
    private FilteredList<Evento>   eventosFiltrados;
    private ArrayList<Evento> eventos= new ArrayList<>();
    private Evento eventoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RepositorioAdmin repo = RepositorioAdmin.getInstance();

        // ── 1. Cargar datos (reemplaza con tu DAO o servicio) ─────────
        todosLosEventos = FXCollections.observableArrayList(repo.getEventos());

        // ── 2. FilteredList para búsqueda en tiempo real ──────────────
        eventosFiltrados = new FilteredList<>(todosLosEventos, e -> true);

        // ── 3. Celda personalizada ────────────────────────────────────
        eventosListView.setCellFactory(lv -> new EventoCell());
        eventosListView.setItems(eventosFiltrados);
        eventosListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, anterior, nuevo) -> {
                    eventoSeleccionado = nuevo;
                });
        eventosListView.setOnMouseClicked(event -> {

            if(event.getClickCount() == 2){
                abrirDetalleEvento(event);
            }
        });

        // ── 4. Llenar ComboBoxes de filtro ────────────────────────────
        filtroCategoria.getItems().add("Todas");
        for (CategoriaEvento c : CategoriaEvento.values())
            filtroCategoria.getItems().add(c.name());
        filtroCategoria.setValue("Todas");

        filtroEstado.getItems().add("Todos");
        for (EstadoEvento s : EstadoEvento.values())
            filtroEstado.getItems().add(s.name());
        filtroEstado.setValue("Todos");

        // ── 5. Contador ───────────────────────────────────────────────
        actualizarContador();
    }

    // ── Filtrado en tiempo real ────────────────────────────────────────
    @FXML
    private void filtrar() {
        String ciudad    = filtroCiudad.getText().trim().toLowerCase();
        String categoria = filtroCategoria.getValue();
        String estado    = filtroEstado.getValue();

        eventosFiltrados.setPredicate(e -> {
            boolean okCiudad    = ciudad.isEmpty()
                    || e.getCiudad().toLowerCase().contains(ciudad);
            boolean okCategoria = categoria == null || categoria.equals("Todas")
                    || e.getCategoria().name().equals(categoria);
            boolean okEstado    = estado == null || estado.equals("Todos")
                    || e.getEstado().name().equals(estado);
            return okCiudad && okCategoria && okEstado;
        });

        actualizarContador();
    }

    private void actualizarContador() {
        int n = eventosFiltrados.size();
        contadorLabel.setText(n + (n == 1 ? " evento" : " eventos"));
    }

    // ── Acciones de botones ───────────────────────────────────────────
    @FXML
    private void irCompras(ActionEvent event) {

        try {

            if (eventoSeleccionado == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Evento");
                alert.setHeaderText(null);
                alert.setContentText("Seleccione un evento");
                alert.showAndWait();
                return;
            }

            Scene escenaAnterior = ((Node) event.getSource()).getScene();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/org/samuel/gestor_eventos/pago.fxml"
                    )
            );

            Parent root = loader.load();

            PagoControler controller = loader.getController();
            controller.setEscenaAnterior(escenaAnterior);
            controller.setEventoSeleccionado(eventoSeleccionado);

            Stage ventana = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ventana.setScene(new Scene(root));
            ventana.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/samuel/gestor_eventos/login.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== VARIABLE DE NAVEGACIÓN ====================
    private Scene escenaAnterior;

    @FXML private Button btnVolver;

    // ==================== MÉTODO PARA RECIBIR ESCENA ANTERIOR ====================
    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    // ==================== MÉTODO VOLVER ====================
    @FXML
    private void volver() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/inicio.fxml");
    }

    private void abrirDetalleEvento(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/org/samuel/gestor_eventos/detalle-evento.fxml"
                    )
            );

            Parent root = loader.load();

            DetalleEventoController controller = loader.getController();

            controller.setEventoSeleccionado(eventoSeleccionado);
            controller.setEscenaAnterior(((Node) event.getSource()).getScene());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}