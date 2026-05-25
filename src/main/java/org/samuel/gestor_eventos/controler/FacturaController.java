package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.modelos.Facturas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class FacturaController {

    @FXML private Label lblFacturaId;
    @FXML private Label lblCliente;
    @FXML private Label lblEvento;
    @FXML private Label lblMonto;
    @FXML private Label lblMetodoPago;
    @FXML private Label lblEstadoCompra;
    @FXML private Button btnVolver;
    @FXML private Button btnDescargarCSV;
    @FXML private Button btnDescargarPDF;

    private Facturas factura;
    private Scene escenaAnterior;

    public void setEscenaAnterior(Scene escenaAnterior) {
        this.escenaAnterior = escenaAnterior;
    }

    @FXML
    private void volver() {
        if (escenaAnterior != null) {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(escenaAnterior);
        }
    }

    // ==================== CARGAR FACTURA ====================

    public void setFactura(Facturas factura) {
        this.factura = factura;
        cargarDatosFactura();
    }

    private void cargarDatosFactura() {
        if (factura == null) {
            return;
        }

        lblFacturaId.setText(String.valueOf(factura.getIdFactura()));
        lblCliente.setText(factura.getCompra().getUsuarioAsociado().getNombre());
        lblEvento.setText(factura.getCompra().getEventoAsociado().getNombre());
        lblMonto.setText("$ " + factura.getPago().getMonto());
        lblMetodoPago.setText(factura.getPago().getMetodoPago());
        lblEstadoCompra.setText(factura.getCompra().getEstado().name());
    }

    // ==================== DESCARGAR CSV ====================

    @FXML
    private void descargarCSV() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Guardar factura CSV");
            chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
            );

            File archivo = chooser.showSaveDialog(btnDescargarCSV.getScene().getWindow());

            if (archivo == null) return;

            FileWriter writer = new FileWriter(archivo);
            writer.write(factura.generarCSV());
            writer.close();

            System.out.println("CSV generado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== DESCARGAR PDF ====================

    @FXML
    private void descargarPDF() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Guardar factura PDF");
            chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
            );

            File archivo = chooser.showSaveDialog(btnDescargarPDF.getScene().getWindow());

            if (archivo == null) return;

            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(archivo));

            document.open();
            document.add(new Paragraph(factura.generarFactura()));
            document.close();

            System.out.println("PDF generado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== ALERTAS ====================

    private void mostrarAlerta(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Factura");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}