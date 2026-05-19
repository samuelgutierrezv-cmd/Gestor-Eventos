package org.samuel.gestor_eventos.controler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navegacion {

    public static void cambiarVentana(
            Stage stage,
            String rutaFXML
    ) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    Navegacion.class.getResource(rutaFXML)
            );

            Parent root = loader.load();

            stage.setScene(new Scene(root));

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}