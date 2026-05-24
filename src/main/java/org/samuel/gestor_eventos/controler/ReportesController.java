package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Zona;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ReportesController implements Initializable {

    @FXML private ComboBox<String> comboTipoReporte;
    @FXML private DatePicker datePickerInicio;
    @FXML private DatePicker datePickerFin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ✅ AGREGAR ITEMS DESDE EL CONTROLADOR
        comboTipoReporte.setItems(FXCollections.observableArrayList(
                "Ventas por Periodo",
                "Ocupación por Zona",
                "Ingresos por Servicios Adicionales",
                "Tasa de Cancelación",
                "Top Eventos"
        ));
        comboTipoReporte.setValue("Ventas por Periodo");
    }

    @FXML
    private void generarCSV() {
        String tipo = comboTipoReporte.getValue();
        LocalDate inicio = datePickerInicio.getValue();
        LocalDate fin = datePickerFin.getValue();

        if (inicio == null || fin == null) {
            mostrarAlerta("Seleccione un rango de fechas");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Guardar reporte CSV");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File archivo = chooser.showSaveDialog(comboTipoReporte.getScene().getWindow());

        if (archivo == null) return;

        try (FileWriter writer = new FileWriter(archivo)) {
            String contenido = generarContenidoReporte(tipo, inicio, fin, "CSV");
            writer.write(contenido);
            mostrarAlerta("Reporte CSV generado correctamente");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error al generar el reporte: " + e.getMessage());
        }
    }

    @FXML
    private void generarPDF() {
        String tipo = comboTipoReporte.getValue();
        LocalDate inicio = datePickerInicio.getValue();
        LocalDate fin = datePickerFin.getValue();

        if (inicio == null || fin == null) {
            mostrarAlerta("Seleccione un rango de fechas");
            return;
        }

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Guardar reporte PDF");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File archivo = chooser.showSaveDialog(comboTipoReporte.getScene().getWindow());

        if (archivo == null) return;

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new java.io.FileOutputStream(archivo));
            document.open();
            String contenido = generarContenidoReporte(tipo, inicio, fin, "PDF");
            document.add(new Paragraph(contenido));
            document.close();
            mostrarAlerta("Reporte PDF generado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error al generar el reporte: " + e.getMessage());
        }
    }

    private String generarContenidoReporte(String tipo, LocalDate inicio, LocalDate fin, String formato) {
        StringBuilder sb = new StringBuilder();

        if (formato.equals("CSV")) {
            sb.append("REPORTE OPERATIVO\n");
            sb.append("Tipo: ").append(tipo).append("\n");
            sb.append("Periodo: ").append(inicio).append(" a ").append(fin).append("\n");
            sb.append("Fecha generación: ").append(LocalDate.now()).append("\n\n");
        } else {
            sb.append("========================================\n");
            sb.append("      REPORTE OPERATIVO\n");
            sb.append("========================================\n");
            sb.append("Tipo: ").append(tipo).append("\n");
            sb.append("Periodo: ").append(inicio).append(" a ").append(fin).append("\n");
            sb.append("Fecha generación: ").append(LocalDate.now()).append("\n");
            sb.append("========================================\n\n");
        }

        RepositorioAdmin repo = RepositorioAdmin.getInstance();

        switch (tipo) {
            case "Ventas por Periodo":
                sb.append(generarVentasPorPeriodo(repo, inicio, fin, formato));
                break;
            case "Ocupación por Zona":
                sb.append(generarOcupacionPorZona(repo, formato));
                break;
            case "Ingresos por Servicios Adicionales":
                sb.append(generarIngresosServicios(repo, formato));
                break;
            case "Tasa de Cancelación":
                sb.append(generarTasaCancelacion(repo, inicio, fin, formato));
                break;
            case "Top Eventos":
                sb.append(generarTopEventos(repo, inicio, fin, formato));
                break;
        }

        return sb.toString();
    }

    private String generarVentasPorPeriodo(RepositorioAdmin repo, LocalDate inicio, LocalDate fin, String formato) {
        StringBuilder sb = new StringBuilder();
        if (formato.equals("CSV")) {
            sb.append("Fecha,Cantidad Ventas,Ingresos Totales\n");
        } else {
            sb.append("VENTAS POR PERIODO\n");
            sb.append("-----------------\n");
        }

        Map<String, Integer> ventasPorDia = new HashMap<>();
        Map<String, Double> ingresosPorDia = new HashMap<>();

        for (Compra c : repo.getCompras()) {
            LocalDate fecha = c.getFechaCompra().toLocalDate();
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                String fechaStr = fecha.toString();
                ventasPorDia.put(fechaStr, ventasPorDia.getOrDefault(fechaStr, 0) + 1);
                if (c.getEstado() == EstadoCompras.PAGADA || c.getEstado() == EstadoCompras.CONFIRMADA) {
                    ingresosPorDia.put(fechaStr, ingresosPorDia.getOrDefault(fechaStr, 0.0) + c.getValor());
                }
            }
        }

        for (String fecha : ventasPorDia.keySet()) {
            if (formato.equals("CSV")) {
                sb.append(fecha).append(",")
                        .append(ventasPorDia.get(fecha)).append(",")
                        .append(ingresosPorDia.getOrDefault(fecha, 0.0)).append("\n");
            } else {
                sb.append("  • ").append(fecha).append(": ")
                        .append(ventasPorDia.get(fecha)).append(" ventas, $")
                        .append(ingresosPorDia.getOrDefault(fecha, 0.0)).append("\n");
            }
        }

        return sb.toString();
    }

    private String generarOcupacionPorZona(RepositorioAdmin repo, String formato) {
        StringBuilder sb = new StringBuilder();
        if (formato.equals("CSV")) {
            sb.append("Zona,Capacidad,Ocupados,Porcentaje\n");
        } else {
            sb.append("OCUPACIÓN POR ZONA\n");
            sb.append("-----------------\n");
        }

        for (Zona zona : repo.getZonas()) {
            int ocupados = 0;
            for (var asiento : zona.getConfiguracionAsientos()) {
                if (asiento.getEstado() == org.samuel.gestor_eventos.enums.EstadoAsiento.VENDIDO ||
                        asiento.getEstado() == org.samuel.gestor_eventos.enums.EstadoAsiento.RESERVADO) {
                    ocupados++;
                }
            }
            double porcentaje = zona.getCapacidad() > 0 ? (ocupados * 100.0) / zona.getCapacidad() : 0;

            if (formato.equals("CSV")) {
                sb.append(zona.getNombre()).append(",")
                        .append(zona.getCapacidad()).append(",")
                        .append(ocupados).append(",")
                        .append(String.format("%.2f", porcentaje)).append("%\n");
            } else {
                sb.append("  • ").append(zona.getNombre()).append(": ")
                        .append(zona.getCapacidad()).append(" capacidad, ")
                        .append(ocupados).append(" ocupados (")
                        .append(String.format("%.2f", porcentaje)).append("%)\n");
            }
        }

        return sb.toString();
    }

    private String generarIngresosServicios(RepositorioAdmin repo, String formato) {
        StringBuilder sb = new StringBuilder();
        if (formato.equals("CSV")) {
            sb.append("Servicio,Cantidad,Ingresos\n");
        } else {
            sb.append("INGRESOS POR SERVICIOS ADICIONALES\n");
            sb.append("----------------------------------\n");
        }

        Map<String, Integer> cantidadServicios = new HashMap<>();
        Map<String, Double> ingresosServicios = new HashMap<>();

        // Precios de servicios
        Map<String, Double> precios = new HashMap<>();
        precios.put("VIP", 100000.0);
        precios.put("Parqueadero", 50000.0);
        precios.put("Seguro", 20000.0);

        for (Compra c : repo.getCompras()) {
            if (c.getEstado() == EstadoCompras.PAGADA || c.getEstado() == EstadoCompras.CONFIRMADA) {
                for (String servicio : c.getServiciosAdicionales()) {
                    cantidadServicios.put(servicio, cantidadServicios.getOrDefault(servicio, 0) + 1);
                    ingresosServicios.put(servicio, ingresosServicios.getOrDefault(servicio, 0.0) + precios.getOrDefault(servicio, 0.0));
                }
            }
        }

        for (String servicio : cantidadServicios.keySet()) {
            if (formato.equals("CSV")) {
                sb.append(servicio).append(",")
                        .append(cantidadServicios.get(servicio)).append(",")
                        .append(ingresosServicios.get(servicio)).append("\n");
            } else {
                sb.append("  • ").append(servicio).append(": ")
                        .append(cantidadServicios.get(servicio)).append(" unidades, $")
                        .append(ingresosServicios.get(servicio)).append("\n");
            }
        }

        return sb.toString();
    }

    private String generarTasaCancelacion(RepositorioAdmin repo, LocalDate inicio, LocalDate fin, String formato) {
        StringBuilder sb = new StringBuilder();
        if (formato.equals("CSV")) {
            sb.append("Total Compras,Canceladas,Tasa Cancelación\n");
        } else {
            sb.append("TASA DE CANCELACIÓN\n");
            sb.append("------------------\n");
        }

        int total = 0;
        int canceladas = 0;

        for (Compra c : repo.getCompras()) {
            LocalDate fecha = c.getFechaCompra().toLocalDate();
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                total++;
                if (c.getEstado() == EstadoCompras.CANCELADA) {
                    canceladas++;
                }
            }
        }

        double tasa = total > 0 ? (canceladas * 100.0) / total : 0;

        if (formato.equals("CSV")) {
            sb.append(total).append(",")
                    .append(canceladas).append(",")
                    .append(String.format("%.2f", tasa)).append("%\n");
        } else {
            sb.append("  Total compras: ").append(total).append("\n");
            sb.append("  Canceladas: ").append(canceladas).append("\n");
            sb.append("  Tasa: ").append(String.format("%.2f", tasa)).append("%\n");
        }

        return sb.toString();
    }

    private String generarTopEventos(RepositorioAdmin repo, LocalDate inicio, LocalDate fin, String formato) {
        StringBuilder sb = new StringBuilder();
        if (formato.equals("CSV")) {
            sb.append("Evento,Ventas,Ingresos\n");
        } else {
            sb.append("TOP EVENTOS\n");
            sb.append("-----------\n");
        }

        Map<String, Integer> ventasPorEvento = new HashMap<>();
        Map<String, Double> ingresosPorEvento = new HashMap<>();

        for (Compra c : repo.getCompras()) {
            LocalDate fecha = c.getFechaCompra().toLocalDate();
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                String nombreEvento = c.getEventoAsociado().getNombre();
                ventasPorEvento.put(nombreEvento, ventasPorEvento.getOrDefault(nombreEvento, 0) + 1);
                if (c.getEstado() == EstadoCompras.PAGADA || c.getEstado() == EstadoCompras.CONFIRMADA) {
                    ingresosPorEvento.put(nombreEvento, ingresosPorEvento.getOrDefault(nombreEvento, 0.0) + c.getValor());
                }
            }
        }

        // Ordenar por ventas
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(ventasPorEvento.entrySet());
        lista.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (int i = 0; i < Math.min(lista.size(), 5); i++) {
            Map.Entry<String, Integer> entry = lista.get(i);
            String evento = entry.getKey();
            if (formato.equals("CSV")) {
                sb.append(evento).append(",")
                        .append(entry.getValue()).append(",")
                        .append(ingresosPorEvento.getOrDefault(evento, 0.0)).append("\n");
            } else {
                sb.append("  #").append(i + 1).append(". ").append(evento).append(": ")
                        .append(entry.getValue()).append(" ventas, $")
                        .append(ingresosPorEvento.getOrDefault(evento, 0.0)).append("\n");
            }
        }

        return sb.toString();
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) comboTipoReporte.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/administrador.fxml");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}