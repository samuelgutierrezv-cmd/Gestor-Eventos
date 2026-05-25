package org.samuel.gestor_eventos.controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.samuel.gestor_eventos.enums.EstadoCompras;
import org.samuel.gestor_eventos.enums.EstadoEvento;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Zona;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MetricasController implements Initializable {

    @FXML private Label lblUsuarios;
    @FXML private Label lblEventos;
    @FXML private Label lblEventosActivos;
    @FXML private Label lblEventosCancelados;
    @FXML private Label lblCompras;
    @FXML private Label lblIngresos;

    @FXML private PieChart pieChartOcupacion;
    @FXML private BarChart<String, Number> barChartIngresos;
    @FXML private LineChart<String, Number> lineChartVentas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RepositorioAdmin repo = RepositorioAdmin.getInstance();

        // ---- DATOS GENERALES ----
        int usuarios = repo.getUsuarios().size();
        int eventos = repo.getEventos().size();
        int activos = 0;
        int cancelados = 0;
        double ingresos = 0;

        for (Evento e : repo.getEventos()) {
            if (e.getEstado() == EstadoEvento.ACTIVO || e.getEstado() == EstadoEvento.PUBLICADO) {
                activos++;
            }
            if (e.getEstado() == EstadoEvento.CANCELADO) {
                cancelados++;
            }
        }

        for (Compra c : repo.getCompras()) {
            if (c.getEstado() == EstadoCompras.PAGADA || c.getEstado() == EstadoCompras.CONFIRMADA) {
                ingresos += c.getValor();
            }
        }

        // ✅ ESTAS LÍNEAS SON LAS QUE CAUSABAN EL ERROR
        lblUsuarios.setText("Usuarios registrados: " + usuarios);
        lblEventos.setText("Eventos totales: " + eventos);
        lblEventosActivos.setText("Eventos activos: " + activos);
        lblEventosCancelados.setText("Eventos cancelados: " + cancelados);
        lblCompras.setText("Compras realizadas: " + repo.getCompras().size());
        lblIngresos.setText("Ingresos totales: $" + ingresos);

        // ---- GRÁFICA DE PASTEL: OCUPACIÓN POR ZONA ----
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        Map<String, Integer> ocupacionPorZona = new HashMap<>();

        for (Zona zona : repo.getZonas()) {
            int ocupados = 0;
            for (var asiento : zona.getConfiguracionAsientos()) {
                if (asiento.getEstado() == org.samuel.gestor_eventos.enums.EstadoAsiento.VENDIDO ||
                        asiento.getEstado() == org.samuel.gestor_eventos.enums.EstadoAsiento.RESERVADO) {
                    ocupados++;
                }
            }
            double porcentaje = zona.getCapacidad() > 0 ?
                    (ocupados * 100.0) / zona.getCapacidad() : 0;
            pieData.add(new PieChart.Data(zona.getNombre() + " (" + Math.round(porcentaje) + "%)", ocupados));
        }
        pieChartOcupacion.setData(pieData);

        // ---- GRÁFICA DE BARRAS: INGRESOS POR EVENTO ----
        XYChart.Series<String, Number> ingresosSeries = new XYChart.Series<>();
        ingresosSeries.setName("Ingresos");

        Map<Integer, Double> ingresosPorEvento = new HashMap<>();
        for (Compra c : repo.getCompras()) {
            if (c.getEstado() == EstadoCompras.PAGADA || c.getEstado() == EstadoCompras.CONFIRMADA) {
                int idEvento = c.getEventoAsociado().getId();
                ingresosPorEvento.put(idEvento, ingresosPorEvento.getOrDefault(idEvento, 0.0) + c.getValor());
            }
        }

        for (Evento e : repo.getEventos()) {
            double ingreso = ingresosPorEvento.getOrDefault(e.getId(), 0.0);
            ingresosSeries.getData().add(new XYChart.Data<>(e.getNombre(), ingreso));
        }
        barChartIngresos.getData().add(ingresosSeries);

        // ---- GRÁFICA DE LÍNEAS: VENTAS POR PERIODO ----
        XYChart.Series<String, Number> ventasSeries = new XYChart.Series<>();
        ventasSeries.setName("Ventas por día");

        // Agrupar compras por fecha
        Map<String, Integer> ventasPorDia = new HashMap<>();
        for (Compra c : repo.getCompras()) {
            String fecha = c.getFechaCompra().toString();
            ventasPorDia.put(fecha, ventasPorDia.getOrDefault(fecha, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : ventasPorDia.entrySet()) {
            ventasSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        lineChartVentas.getData().add(ventasSeries);
    }

    @FXML
    private void volver() {
        Stage stage = (Stage) lblUsuarios.getScene().getWindow();
        Navegacion.cambiarVentana(stage, "/org/samuel/gestor_eventos/administrador.fxml");
    }
}