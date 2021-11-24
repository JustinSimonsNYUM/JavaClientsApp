package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class reportsController {

    @FXML
    private ComboBox<?> reportApptMonth;

    @FXML
    private Label reportApptResult;

    @FXML
    private ComboBox<?> reportApptType;

    @FXML
    private TableColumn<?, ?> reportScheduleApptID;

    @FXML
    private ComboBox<?> reportScheduleChooseContact;

    @FXML
    private TableColumn<?, ?> reportScheduleCustomerID;

    @FXML
    private TableColumn<?, ?> reportScheduleDescription;

    @FXML
    private TableColumn<?, ?> reportScheduleEndDaT;

    @FXML
    private TableColumn<?, ?> reportScheduleStartDaT;

    @FXML
    private TableView<?> reportScheduleTable;

    @FXML
    private TableColumn<?, ?> reportScheduleTitle;

    @FXML
    private TableColumn<?, ?> reportScheduleType;

    Stage stage;
    Parent scene;

    @FXML
    void reportApptCalculateButton(ActionEvent event) {

    }
    @FXML
    void reportApptReturnButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    @FXML
    void reportOtherReturnButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    @FXML
    void reportScheduleReturnButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
}