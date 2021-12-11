package controller;
/**
 * class addApptController.java
 */

/**
 * @author Justin Simons
 * */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * class logInController logs the user in
 */
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

    /**
     * initialize sets login screen to either french or english.
     * sets the exit button, log in button, zone id, password label, and user name to english or french
     */
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

    /**
     * logInButton logs the user in.
     * it first gets the text entered for the userID and password.
     * it checks to make sure that both userID and password are "test"
     * if it is then it opens up the main page. and also checks for upcoming appointments in the next 15 minutes.
     * @param event is called when the log in button is clicked.
     * @throws IOException thrown if can't load the scene.f
     */
    @FXML
    void logInButton(ActionEvent event) throws IOException{
        String userId = userIdLogIn.getText();
        String password = passwordLogIn.getText();

        ResourceBundle rb = ResourceBundle.getBundle("RB", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle((String) rb.getObject("errorTitle"));
        alert.setHeaderText((String) rb.getObject("errorHeader"));

        if((userId.equals("test")) && (password.equals("test"))){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
            stage.setScene(new Scene(scene,1235,558));
            stage.show();
            Appointments.checkUpcomingAppt();
        }
        else if(userId.isEmpty() || password.isEmpty()){
            alert.setContentText((String) rb.getObject("alertEmpty"));
            alert.show();
        }
        else {
            alert.setContentText((String) rb.getObject("alertWrong"));
            alert.show();
        }
    }

    /**
     * logInFormExitButton closes the program
     * @param event called when exit button is called
     */
    @FXML
    void logInFormExitButton(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

}