package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class editApptController {
    @FXML
    private TextField editApptApptTitle;

    @FXML
    private ComboBox<?> editApptContact;

    @FXML
    private ComboBox<?> editApptCustomerID;

    @FXML
    private DatePicker editApptDate;

    @FXML
    private TextField editApptDescription;

    @FXML
    private ComboBox<?> editApptEndTime;

    @FXML
    private TextField editApptID;

    @FXML
    private TextField editApptLocation;

    @FXML
    private ComboBox<?> editApptStartTime;

    @FXML
    private ComboBox<?> editApptType;

    @FXML
    private ComboBox<?> editApptUserID;

    Stage stage;
    Parent scene;

    @FXML
    void editApptCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    @FXML
    void editApptSubmitButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
}