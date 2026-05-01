package org.samuel.gestor_eventos.controler;
import org.samuel.gestor_eventos.modelos.Zona;
import org.samuel.gestor_eventos.enums.Sector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.samuel.gestor_eventos.enums.CategoriaEvento;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Recinto;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EventosControler implements Initializable {

    @FXML private ListView<Evento>      eventosListView;
    @FXML private Label                 contadorLabel;
    @FXML private TextField             filtroCiudad;
    @FXML private ComboBox<String>      filtroCategoria;
    @FXML private ComboBox<String>      filtroEstado;

    private ObservableList<Evento> todosLosEventos;
    private FilteredList<Evento>   eventosFiltrados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // ── 1. Cargar datos (reemplaza con tu DAO o servicio) ─────────
        todosLosEventos = FXCollections.observableArrayList(cargarEventosDePrueba());

        // ── 2. FilteredList para búsqueda en tiempo real ──────────────
        eventosFiltrados = new FilteredList<>(todosLosEventos, e -> true);

        // ── 3. Celda personalizada ────────────────────────────────────
        eventosListView.setCellFactory(lv -> new EventoCell());
        eventosListView.setItems(eventosFiltrados);

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
    private void irCrearEvento() {
        // TODO: cargar la vista de creación
        System.out.println("Navegar a crear evento");
    }

    @FXML
    private void cerrarSesion() {
        // TODO: volver a login
        System.out.println("Cerrar sesión");
    }

    // ── Datos de ejemplo ─────────────────────────────────────────────
    // Reemplaza este método por una llamada a tu EventoDAO
    private List<Evento> cargarEventosDePrueba() {

        ArrayList<Zona> conjunto = new ArrayList<>();
        Zona z1 = new Zona.ZonaBuilder(10000, 1, Sector.GENERAL, "Andes", 100).builder();
        Zona z2 = new Zona.ZonaBuilder(15000, 2, Sector.VIP, "Monteblanco", 50).builder();

        conjunto.add(z1);
        conjunto.add(z2);

        Recinto r1 = new Recinto(1, "Estadio El Campin",   conjunto,  "Bogota", "recinto a");
        Recinto r2 = new Recinto(2, "Teatro Metropolitano",conjunto, "Medellin", "recinto b");
        Recinto r3 = new Recinto(3, "Anfiteatro del Lago", conjunto,  "Bogota", "recinto c");

        LocalDate fechaHoy = LocalDate.now();
        return List.of(
                new Evento(
                        CategoriaEvento.CONCIERTO, 1,
                        "Festival Estéreo Picnic",
                        "Conciertos simultáneos en 4 escenarios",
                        "El festival de música más grande de Colombia",
                        "Bogota",
                        fechaHoy, "16:00",
                        "No reembolsos 48h antes del evento",
                        r3, EstadoEvento.ACTIVO
                ),
                new Evento(
                        CategoriaEvento.DEPORTES, 2,
                        "Clásico Millonarios vs Santa Fe",
                        "Partido de ida ida de la Copa Colombia",
                        "El clásico capitalino más esperado del año",
                        "Bogota",
                        fechaHoy, "19:30",
                        "Reembolso hasta 24h antes",
                        r1, EstadoEvento.ACTIVO
                ),
                new Evento(
                        CategoriaEvento.TEATRO, 3,
                        "Hamlet — Versión Contemporánea",
                        "Adaptación moderna con elenco nacional",
                        "La obra más aclamada de la temporada 2025",
                        "Medellin",
                        fechaHoy, "20:00",
                        "Cambio de fecha permitido con 72h de anticipación",
                        r2, EstadoEvento.BORRADOR
                ),
                new Evento(
                        CategoriaEvento.CONFERENCIA, 4,
                        "DevFest Colombia 2025",
                        "Charlas de IA, Cloud y Mobile",
                        "Conferencia de tecnología Google para desarrolladores",
                        "Bogota",
                        fechaHoy, "08:30",
                        "Sin reembolso. Transferencia de entrada permitida",
                        r1, EstadoEvento.CANCELADO
                ),
                new Evento(
                        CategoriaEvento.FESTIVAL, 5,
                        "Feria de Cali 2025",
                        "Salsa, gastronomía y cultura vallecaucana",
                        "La fiesta más importante del suroccidente colombiano",
                        "Cali",
                        fechaHoy, "12:00",
                        "Política de reembolso total si se cancela el evento",
                        r3, EstadoEvento.FINALIZADO
                )
        );
    }
}
