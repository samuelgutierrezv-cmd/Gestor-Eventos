package org.samuel.gestor_eventos.controler;


import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.samuel.gestor_eventos.enums.CategoriaEvento;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.modelos.Evento;

import java.time.format.DateTimeFormatter;

public class EventoCell extends ListCell<Evento> {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd MMM yyyy");

    // ── Nodos reutilizables ──────────────────────────────────────────
    private final HBox  card       = new HBox();
    private final Label acento     = new Label();      // barra lateral de color
    private final Label icon       = new Label();      // emoji por categoría
    private final VBox  body       = new VBox(5);      // columna central
    private final VBox  side       = new VBox(8);      // columna derecha
    private final Pane  spacer     = new Pane();

    // Body
    private final Label lblNombre      = new Label();
    private final Label lblDescripcion = new Label();
    private final HBox  metaRow        = new HBox(16);
    private final Label lblCiudad      = new Label();
    private final Label lblFecha       = new Label();
    private final Label lblHora        = new Label();
    private final Label lblRecinto     = new Label();
    private final Label lblActividad   = new Label();

    // Side
    private final Label badgeCategoria = new Label();
    private final Label badgeEstado    = new Label();
    private final Label lblPolitica    = new Label();

    public EventoCell() {
        super();

        // ── Estilos base ──
        card.getStyleClass().add("evento-card");
        body.getStyleClass().add("evento-body");
        side.getStyleClass().add("evento-side");

        icon.getStyleClass().add("evento-icon");
        lblNombre.getStyleClass().add("evento-nombre");
        lblDescripcion.getStyleClass().add("evento-descripcion");
        metaRow.getStyleClass().add("evento-meta-row");
        lblCiudad.getStyleClass().add("evento-meta");
        lblFecha.getStyleClass().add("evento-meta");
        lblHora.getStyleClass().add("evento-meta");
        lblRecinto.getStyleClass().add("evento-meta");
        lblActividad.getStyleClass().add("evento-actividad");
        badgeCategoria.getStyleClass().add("badge-categoria");
        lblPolitica.getStyleClass().add("evento-politica");

        lblDescripcion.setWrapText(true);
        lblDescripcion.setMaxWidth(420);
        lblPolitica.setWrapText(true);

        // ── Ensamble de nodos ──
        metaRow.setAlignment(Pos.CENTER_LEFT);
        metaRow.getChildren().addAll(lblCiudad, lblFecha, lblHora, lblRecinto);

        body.setAlignment(Pos.CENTER_LEFT);
        body.getChildren().addAll(lblNombre, lblDescripcion, metaRow, lblActividad);
        HBox.setHgrow(body, Priority.ALWAYS);

        side.setAlignment(Pos.TOP_RIGHT);
        side.getChildren().addAll(badgeCategoria, badgeEstado, spacer, lblPolitica);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Caja del icono
        VBox iconBox = new VBox(icon);
        iconBox.getStyleClass().add("evento-icon-box");
        iconBox.setAlignment(Pos.CENTER);

        card.getChildren().addAll(acento, iconBox, body, side);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMaxWidth(Double.MAX_VALUE);

        setText(null);
    }

    @Override
    protected void updateItem(Evento e, boolean empty) {
        super.updateItem(e, empty);

        if (empty || e == null) {
            setGraphic(null);
            return;
        }

        // ── Icono según categoría ──
        icon.setText(iconoDeCategoria(e.getCategoria()));

        // ── Franja lateral según estado ──
        acento.getStyleClass().removeAll(
                "acento-activo","acento-borrador","acento-cancelado","acento-finalizado");
        acento.getStyleClass().add(acentoDeEstado(e.getEstado()));

        // ── Badge estado ──
        badgeEstado.getStyleClass().removeAll(
                "badge-activo","badge-borrador","badge-cancelado","badge-finalizado");
        badgeEstado.getStyleClass().add(badgeDeEstado(e.getEstado()));
        badgeEstado.setText(e.getEstado().name());

        // ── Body ──
        lblNombre.setText("#" + e.getId() + "  " + e.getNombre());
        lblDescripcion.setText(e.getDescripcion());
        lblCiudad.setText("📍 " + e.getCiudad());
        lblFecha.setText("? " + (e.getFecha() != null ? e.getFecha().format(DTF) : "?"));
        lblHora.setText("🕐 " + e.getHora());
        lblRecinto.setText("🏟 " + (e.getRecinto() != null ? e.getRecinto().getNombre() : "Sin recinto"));
        lblActividad.setText(
                e.getActividadProgramada() != null && !e.getActividadProgramada().isBlank()
                        ? "▸ " + e.getActividadProgramada()
                        : "");

        // ── Side ──
        badgeCategoria.setText(e.getCategoria().name());
        lblPolitica.setText(
                e.getPolitica() != null && !e.getPolitica().isBlank()
                        ? "📄 " + e.getPolitica()
                        : "");

        setGraphic(card);
    }

    // ── Helpers ────────────────────────────────────────────────────
    private String iconoDeCategoria(CategoriaEvento cat) {
        if (cat == null) return "🎫";
        return switch (cat.name()) {
            case "CONCIERTO"   -> "🎵";
            case "DEPORTES"    -> "⚽";
            case "TEATRO"      -> "🎭";
            case "CONFERENCIA" -> "🎤";
            case "FESTIVAL"    -> "🎪";
            case "EXPOSICION"  -> "🎨";
            default            -> "🎫";
        };
    }

    private String acentoDeEstado(EstadoEvento estado) {
        if (estado == null) return "acento-borrador";
        return switch (estado.name()) {
            case "ACTIVO"     -> "acento-activo";
            case "CANCELADO"  -> "acento-cancelado";
            case "FINALIZADO" -> "acento-finalizado";
            default           -> "acento-borrador";
        };
    }

    private String badgeDeEstado(EstadoEvento estado) {
        if (estado == null) return "badge-borrador";
        return switch (estado.name()) {
            case "ACTIVO"     -> "badge-activo";
            case "CANCELADO"  -> "badge-cancelado";
            case "FINALIZADO" -> "badge-finalizado";
            default           -> "badge-borrador";
        };
    }
}
