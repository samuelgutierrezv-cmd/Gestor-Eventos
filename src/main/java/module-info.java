module org.samuel.gestor_eventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;

    opens org.samuel.gestor_eventos to javafx.fxml;
    opens org.samuel.gestor_eventos.modelos to javafx.base;

    exports org.samuel.gestor_eventos;
    exports org.samuel.gestor_eventos.controler;
    opens org.samuel.gestor_eventos.controler to javafx.fxml;
}