package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormularioControler {

    @FXML
    private VBox contenedorCampos;

    @FXML
    private Label lblTitulo;

    private final Map<String, Node> campos = new HashMap<>();

    public void setTitulo(String titulo) {
        lblTitulo.setText(titulo);
    }

    public void agregarTexto(String key, String placeholder) {
        TextField txt = new TextField();
        txt.setPromptText(placeholder);
        campos.put(key, txt);
        contenedorCampos.getChildren().add(txt);
    }

    public void agregarFecha(String key) {
        DatePicker dp = new DatePicker();
        campos.put(key, dp);
        contenedorCampos.getChildren().add(dp);
    }

    public void agregarComboBox(String key, String placeholder, List<String> opciones) {
        ComboBox<String> combo = new ComboBox<>();
        combo.setPromptText(placeholder);
        combo.getItems().addAll(opciones);
        combo.setMaxWidth(Double.MAX_VALUE);
        campos.put(key, combo);
        contenedorCampos.getChildren().add(combo);
    }

    public Map<String, String> getDatos() {
        Map<String, String> data = new HashMap<>();
        for (var e : campos.entrySet()) {
            Node n = e.getValue();
            if (n instanceof TextField t) {
                data.put(e.getKey(), t.getText());
            } else if (n instanceof DatePicker d) {
                data.put(e.getKey(), String.valueOf(d.getValue()));
            } else if (n instanceof ComboBox<?> c) {
                data.put(e.getKey(), String.valueOf(c.getValue()));
            }
        }
        return data;
    }

    public void agregarListaCheckBox(String key, String titulo, List<String> opciones) {
        VBox contenedor = new VBox(8);
        contenedor.setStyle("-fx-padding: 10; -fx-background-color: #1e293b; -fx-background-radius: 8;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        contenedor.getChildren().add(lblTitulo);

        Map<String, Boolean> valores = new HashMap<>();
        for (String opcion : opciones) {
            CheckBox cb = new CheckBox(opcion);
            cb.setStyle("-fx-text-fill: #e2e8f0;");
            contenedor.getChildren().add(cb);
            valores.put(opcion, false);
        }

        // Guardar referencia para obtener datos
        campos.put(key, contenedor);
    }

    @FXML
    public void guardar() {
        Stage stage = (Stage) contenedorCampos.getScene().getWindow();
        stage.close();
    }
}