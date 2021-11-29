module com.example.javaclientsapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.javaclientsapp to javafx.fxml;
    opens model to javafx.fxml;
    opens controller to javafx.fxml;


    exports com.example.javaclientsapp;
    exports model;
    exports controller;

}
/*
module sample {
    requires javafx.controls;
    requires javafx.fxml;

    opens sample to javafx.fxml;
    opens sample.model to javafx.fxml;

    exports sample;
    exports sample.model;
}
 */