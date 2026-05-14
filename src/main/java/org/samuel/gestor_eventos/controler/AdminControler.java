package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.samuel.gestor_eventos.interfaces.creacion.EventoComponente;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryCompras;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryEventos;
import org.samuel.gestor_eventos.interfaces.creacion.FactoryUsusarios;

import java.util.Map;

import static org.samuel.gestor_eventos.controler.TipoFormulario.*;

public class AdminControler {

    private Scene escenaAnterior;
    private FactoryUsusarios crearUsuarios = new FactoryUsusarios();
    private FactoryEventos crearEventos = new FactoryEventos();
    private FactoryCompras crearCompras = new FactoryCompras();
    @FXML
    private Button btnVolver;

    // ==================== MÉTODO PARA RECIBIR ESCENA ANTERIOR ====================
    public void setEscenaAnterior(Scene escena) {
        this.escenaAnterior = escena;
    }

    // ==================== MÉTODO VOLVER ====================
    @FXML
    private void volver() {
        if (escenaAnterior != null) {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(escenaAnterior);
        }
    }

    // ── USUARIOS ─────────────────────────────────────────────────────
    @FXML private void abrirActualizarUsuario() {  }
    @FXML private void abrirCrearUsuario()      {
        abrirFormulario(TipoFormulario.USUARIO);
    }
    @FXML private void abrirBorrarUsuario()     {  }
    @FXML private void abrirBuscarUsuario()     {  }

    // ── EVENTOS ──────────────────────────────────────────────────────
    @FXML private void abrirActualizarEvento()  {

    }
    @FXML private void abrirCrearEvento()       {
        abrirFormulario(EVENTO);
    }
    @FXML private void abrirBorrarEvento()      {

    }
    @FXML private void abrirBuscarEvento(){

    }

    // ── ASIENTOS ─────────────────────────────────────────────────────
    @FXML private void abrirActualizarAsiento() {  }
    @FXML private void abrirCrearAsiento()      {
        abrirFormulario(ASIENTO);
    }
    @FXML private void abrirBorrarAsiento()     {  }
    @FXML private void abrirBuscarAsiento()     { }

    // ── ZONAS ────────────────────────────────────────────────────────
    @FXML private void abrirActualizarZona()    {  }
    @FXML private void abrirCrearZona()         {
        abrirFormulario(ZONA);
    }
    @FXML private void abrirBorrarZona()        {  }
    @FXML private void abrirBuscarZona()        {  }

    // ── RECINTOS ─────────────────────────────────────────────────────
    @FXML private void abrirActualizarRecinto() {  }
    @FXML private void abrirCrearRecinto()      {
        abrirFormulario(TipoFormulario.RECINTO);
    }
    @FXML private void abrirBorrarRecinto()     { }
    @FXML private void abrirBuscarRecinto()     {  }

    // ── VOLVER ───────────────────────────────────────────────────────


    // ── MÉTODO CENTRAL que abre el formulario ────────────────────────
    private void abrirFormulario(TipoFormulario tipo) {
        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/samuel/gestor_eventos/formulario.fxml")
            );

            Parent root = loader.load();
            FormularioControler c = loader.getController();

            switch (tipo) {

                case USUARIO -> {
                    c.setTitulo("Crear Usuario");
                    c.agregarTexto("nombre", "Nombre");
                    c.agregarTexto("correo", "Correo");
                    c.agregarTexto("telefono", "Teléfono");
                    c.agregarTexto("password", "Password");
                }

                case EVENTO -> {
                    c.setTitulo("Crear Evento");
                    c.agregarTexto("nombre", "Nombre evento");
                    c.agregarTexto("descripcion", "Descripción");
                    c.agregarTexto("ciudad", "Ciudad");
                    c.agregarFecha("fecha");
                }

                case ASIENTO -> {
                    c.setTitulo("Crear Asiento");
                    c.agregarTexto("numero", "Número");
                    c.agregarTexto("fila", "Fila");
                }

                case RECINTO -> {
                    c.setTitulo("Crear Recinto");
                    c.agregarTexto("nombre", "Nombre");
                    c.agregarTexto("direccion", "Dirección");
                    c.agregarTexto("ciudad", "Ciudad");
                }

                case ZONA -> {
                    c.setTitulo("Crear Zona");
                    c.agregarTexto("nombre", "Nombre");
                    c.agregarTexto("capacidad", "Capacidad");
                    c.agregarTexto("precio", "Precio base");
                }

                case INCIDENCIA -> {
                    c.setTitulo("Incidencia");
                    c.agregarTexto("descripcion", "Descripción");
                }
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Map<String, String> datos = c.getDatos();

            procesar(tipo, datos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void procesar(TipoFormulario tipo, Map<String, String> d) {

        switch (tipo) {
            case USUARIO -> {
                System.out.println("Crear Usuario");
            }

            case EVENTO -> {
                System.out.println("Crear Evento");
            }

            case ASIENTO -> {
                System.out.println("creando asiento");
            }

            case RECINTO -> {
                System.out.println("creando recinto");
            }

            case ZONA -> {
                System.out.println("crenado Zona");
            }

            case INCIDENCIA -> {
                System.out.println("creando Incidencia");
            }
        }
    }
}
