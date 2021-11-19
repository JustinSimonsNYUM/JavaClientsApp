package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class logInController {


    @FXML
    private Button exitButton;

    @FXML
    private Button logInButton;

    @FXML
    private Label logInZoneId;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private PasswordField passwordLogIn;

    @FXML
    private TextField userIdLogIn;

    @FXML
    private void initialize(){
        ResourceBundle rb = ResourceBundle.getBundle("RB", Locale.getDefault());
        exitButton.setText(rb.getString("exitButton"));
        logInButton.setText(rb.getString("logInButton"));
        logInZoneId.setText(rb.getString("logInZoneId"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        userNameLabel.setText(rb.getString("userNameLabel"));
    }

    Stage stage;
    Parent scene;

    @FXML
    void logInButton(ActionEvent event) throws IOException{
        String userId = userIdLogIn.getText();
        String password = passwordLogIn.getText();
        System.out.println("hi");
        ResourceBundle rb = ResourceBundle.getBundle("RB", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle((String) rb.getObject("errorTitle"));
        alert.setHeaderText((String) rb.getObject("errorHeader"));

        if((userId.equals("test")) && (password.equals("test"))){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
            stage.setScene(new Scene(scene,400,400));
            stage.show();
        }
        else if(userId.isEmpty() || password.isEmpty()){
            alert.setContentText((String) rb.getObject("alertEmpty"));
            alert.show();
        }
        else if((!userId.equals("test")) || (!password.equals("test"))){
            alert.setContentText((String) rb.getObject("alertWrong"));
            alert.show();
        }
    }

    @FXML
    void logInFormExitButton(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

}