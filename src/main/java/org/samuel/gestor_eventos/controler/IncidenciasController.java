package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.Entidades;
import org.samuel.gestor_eventos.enums.TipoIncidencia;
import org.samuel.gestor_eventos.modelos.Incidencia;

import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class IncidenciasController implements Initializable {

    @FXML private TableView<Incidencia> tablaIncidencias;
    @FXML private TableColumn<Incidencia, Integer> colId;
    @FXML private TableColumn<Incidencia, String> colTipo;
    @FXML private TableColumn<Incidencia, String> colEntidad;
    @FXML private TableColumn<Incidencia, Date> colFecha;
    @FXML private TableColumn<Incidencia, String> colDescripcion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colEntidad.setCellValueFactory(new PropertyValueFactory<>("entidadAfectada"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Cargar incidencias del repositorio
        cargarIncidencias();
    }

    private void cargarIncidencias() {
        var incidencias = FXCollections.observableArrayList(
                RepositorioAdmin.getInstance().getIncidencias()
        );
        tablaIncidencias.setItems(incidencias);
        tablaIncidencias.refresh();
    }

    @FXML
    private void refrescar() {
        cargarIncidencias();
    }

    @FXML
    private void crearIncidencia() {
        abrirFormulario();
    }

    private void abrirFormulario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/samuel/gestor_eventos/formulario.fxml"));
            Parent root = loader.load();
            FormularioControler c = loader.getController();

            c.setTitulo("Nueva Incidencia");
            c.agregarTexto("descripcion", "Descripción de la incidencia");

            // Selección de Entidad Afectada
            List<String> entidades = Arrays.stream(Entidades.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            c.agregarComboBox("entidadAfectada", "Seleccione Entidad Afectada", entidades);

            // Selección de Tipo de Incidencia
            List<String> tipos = Arrays.stream(TipoIncidencia.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            c.agregarComboBox("tipo", "Seleccione Tipo de Incidencia", tipos);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Map<String, String> datos = c.getDatos();
            procesar(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void procesar(Map<String, String> datos) {
        try {
            String descripcion = datos.get("descripcion");
            String entidadStr = datos.get("entidadAfectada");
            String tipoStr = datos.get("tipo");

            if (descripcion == null || descripcion.trim().isEmpty()) {
                mostrarError("La descripción no puede estar vacía");
                return;
            }

            if (entidadStr == null || entidadStr.equals("null")) {
                mostrarError("Debe seleccionar una entidad afectada");
                return;
            }

            if (tipoStr == null || tipoStr.equals("null")) {
                mostrarError("Debe seleccionar un tipo de incidencia");
                return;
            }

            Entidades entidad = Entidades.valueOf(entidadStr);
            TipoIncidencia tipo = TipoIncidencia.valueOf(tipoStr);
            int id = RepositorioAdmin.getInstance().getIncidencias().size() + 1;

            Incidencia incidencia = new Incidencia(
                    descripcion,
                    id,
                    tipo,
                    new Date(System.currentTimeMillis()),
                    entidad
            );
            RepositorioAdmin.getInstance().getIncidencias().add(incidencia);

            cargarIncidencias();

            mostrarMensaje("Incidencia creada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al crear la incidencia: " + e.getMessage());
        }
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) tablaIncidencias.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/administrador.fxml");
    }

    // ---------------- MÉTODOS AUXILIARES ----------------
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}