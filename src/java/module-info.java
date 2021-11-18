module com.example.javaclientsapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.javaclientsapp to javafx.fxml;
    exports com.example.javaclientsapp;
    exports controller;
    opens controller to javafx.fxml;
}