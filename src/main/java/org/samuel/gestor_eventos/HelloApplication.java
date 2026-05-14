package org.samuel.gestor_eventos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.samuel.gestor_eventos.modelos.Compra;
import org.samuel.gestor_eventos.modelos.Evento;
import org.samuel.gestor_eventos.modelos.Facturas;
import org.samuel.gestor_eventos.validaciones.ValidacionesEnteros;
import org.samuel.gestor_eventos.validaciones.ValidacionesTexto;
import org.samuel.gestor_eventos.validaciones.ValidarFechas;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setTitle("Gestor de Eventos");
        stage.setResizable(false);
        stage.show();
        System.out.println("Hello Application");
    }
}
