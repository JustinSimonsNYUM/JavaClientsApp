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

public class editCustomerController {
    @FXML
    private TextField editCustomerAddress;

    @FXML
    private ComboBox<?> editCustomerCountry;

    @FXML
    private ComboBox<?> editCustomerDivision;

    @FXML
    private TextField editCustomerID;

    @FXML
    private TextField editCustomerName;

    @FXML
    private TextField editCustomerPhone;

    @FXML
    private TextField editCustomerPostal;

    Stage stage;
    Parent scene;

    @FXML
    void editCustomerCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    @FXML
    void editCustomerSubmitButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

}