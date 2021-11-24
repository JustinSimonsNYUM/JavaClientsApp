package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class addCustomerController {
    @FXML
    private TextField addNewCustomerAddress;

    @FXML
    private ComboBox<?> addNewCustomerCountry;

    @FXML
    private ComboBox<?> addNewCustomerDivision;

    @FXML
    private TextField addNewCustomerID;

    @FXML
    private TextField addNewCustomerName;

    @FXML
    private TextField addNewCustomerPhone;

    @FXML
    private TextField addNewCustomerPostal;

    Stage stage;
    Parent scene;

    @FXML
    void addNewCustomerCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    @FXML
    void addNewCustomerSubmitButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
}