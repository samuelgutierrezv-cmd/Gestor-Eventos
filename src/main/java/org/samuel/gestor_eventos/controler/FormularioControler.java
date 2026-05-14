package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class FormularioControler {

    @FXML private VBox contenedorCampos;
    @FXML private Label lblTitulo;

    private final Map<String, Node> campos = new HashMap<>();

    // ---------------- CONFIGURACIÓN ----------------

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

    // ---------------- OBTENER DATOS ----------------

    public Map<String, String> getDatos() {
        Map<String, String> data = new HashMap<>();

        for (var e : campos.entrySet()) {
            Node n = e.getValue();

            if (n instanceof TextField t) {
                data.put(e.getKey(), t.getText());
                if (t.getPromptText() != null) {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                    System.out.println("El usuario no se esncuentra en la base de datos ni es usuario admin");
                }
            }

            if (n instanceof DatePicker d) {
                data.put(e.getKey(), String.valueOf(d.getValue()));
            }
        }
        return data;
    }

    // ---------------- GUARDAR ----------------

    @FXML
    public void guardar() {
        Stage stage = (Stage) contenedorCampos.getScene().getWindow();
        stage.close();
    }
}