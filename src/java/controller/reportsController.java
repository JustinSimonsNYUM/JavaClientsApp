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

    @FXML
    private Label reportsTotalApptsApr;

    @FXML
    private Label reportsTotalApptsAug;

    @FXML
    private Label reportsTotalApptsDBrief;

    @FXML
    private Label reportsTotalApptsDec;

    @FXML
    private Label reportsTotalApptsFeb;

    @FXML
    private Label reportsTotalApptsJan;

    @FXML
    private Label reportsTotalApptsJuly;

    @FXML
    private Label reportsTotalApptsJune;

    @FXML
    private Label reportsTotalApptsMar;

    @FXML
    private Label reportsTotalApptsMay;

    @FXML
    private Label reportsTotalApptsNov;

    @FXML
    private Label reportsTotalApptsOct;

    @FXML
    private Label reportsTotalApptsPlanning;

    @FXML
    private Label reportsTotalApptsSep;

    @FXML
    private Label reportsTotalApptsTrain;

    @FXML
    private Label reportsTotalCustomersCanada;

    @FXML
    private Label reportsTotalCustomersUK;

    @FXML
    private Label reportsTotalCustomersUS;

    @FXML
    void reportScheduleChooseContactChosen(ActionEvent event) {

    }

    Stage stage;
    Parent scene;

    @FXML
    void reportApptReturnButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    @FXML
    void reportCustomersReturnButton(ActionEvent event) throws IOException {
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