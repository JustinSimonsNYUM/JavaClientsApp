package controller;

import helper.DBQuery;
import helper.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customers;
import model.Tables;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class addApptController implements Initializable {
    @FXML
    private ComboBox<String> addNewApptContact;

    @FXML
    private ComboBox<Integer> addNewApptCustomerID;

    @FXML
    private DatePicker addNewApptDate;

    @FXML
    private TextField addNewApptDescription;

    @FXML
    private ComboBox<String> addNewApptEndTime;

    @FXML
    private TextField addNewApptID;

    @FXML
    private TextField addNewApptLocation;

    @FXML
    private ComboBox<String> addNewApptStartTime;

    @FXML
    private TextField addNewApptTitle;

    @FXML
    private ComboBox<String> addNewApptType;

    @FXML
    private ComboBox<Integer> addNewApptUserID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void fillComboBoxes() throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        //fill userId combo box
        try{
            String query = "SELECT * from users";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                int userID = rs.getInt("User_ID");
                addNewApptUserID.getItems().add(userID);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //fill CustomerID comboBox
        ObservableList<Customers> customerCBox = Tables.getAllCustomers();
        for(Customers customer : customerCBox){
            addNewApptCustomerID.getItems().add(customer.getId());
        }

        //fill Contacts combo box
        try{
            String query = "SELECT * from contacts";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String contact = rs.getString("Contact_Name");
                addNewApptContact.getItems().add(contact);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //fill type combo box
        addNewApptType.getItems().add("Planning Session");
        addNewApptType.getItems().add("De-Briefing");
        addNewApptType.getItems().add("Training");

        //fill start time combo box
        // business hours defined as 8:00 a.m. to 10:00 p.m. make it so it shows local time appts.
        addNewApptStartTime.getItems().add("");

    }

    Stage stage;
    Parent scene;

    @FXML
    void addNewApptCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    @FXML
    void addNewApptSubmitButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }


}