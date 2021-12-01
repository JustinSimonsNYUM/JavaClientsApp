package controller;

import helper.DBQuery;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Customers;
import model.Tables;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {
    @FXML
    private TextField addNewCustomerAddress;

    @FXML
    private ComboBox<String> addNewCustomerCountry;

    @FXML
    private ComboBox<String> addNewCustomerDivision;

    @FXML
    private TextField addNewCustomerID;

    @FXML
    private TextField addNewCustomerName;

    @FXML
    private TextField addNewCustomerPhone;

    @FXML
    private TextField addNewCustomerPostal;

    public void initialize(URL url, ResourceBundle resourceBundle){
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
        try{
            String query = "SELECT * from countries";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String country = rs.getString("Country");
                addNewCustomerCountry.getItems().add(country);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        try{
            String query = "SELECT * from first_level_divisions";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String division = rs.getString("Division");
                addNewCustomerDivision.getItems().add(division);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static int customerID = 3;

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
    void addNewCustomerDivisionClicked(MouseEvent event) throws SQLException {
        String selectedCountry = addNewCustomerCountry.getValue();
        if(selectedCountry == null) {
            return;
        }
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        int selectedCountryID = 0;
        int divisionCountryID;
        try{
            String query = "SELECT * from countries";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String nextCountry = rs.getString("Country");
                if(nextCountry.equals(selectedCountry))
                    selectedCountryID = rs.getInt("Country_ID");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        try{
            addNewCustomerDivision.getItems().clear();
            String query = "SELECT * from first_level_divisions";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                divisionCountryID = rs.getInt("Country_ID");
                if(divisionCountryID == selectedCountryID)
                    addNewCustomerDivision.getItems().add(rs.getString("Division"));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void addNewCustomerSubmitButton(ActionEvent event) throws IOException, SQLException {
        String name = addNewCustomerName.getText().trim();
        String address = addNewCustomerAddress.getText().trim();
        String postal = addNewCustomerPostal.getText().trim();
        String selectedCountry = addNewCustomerCountry.getValue();
        String selectedDivision = addNewCustomerDivision.getValue();
        String phone = addNewCustomerPhone.getText().trim();

        if( (selectedCountry == null) || (selectedDivision == null) || name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty()){
            myAlert("Please fill out each field.");
            return;
        }
        //get new customer data
        customerID++;
        LocalDate createDateDate = LocalDate.now();
        LocalTime createDateTime = LocalTime.now();
        LocalDateTime createDate = LocalDateTime.of(createDateDate,createDateTime).truncatedTo(ChronoUnit.MINUTES);
        String createdBy = "script";
        LocalDate lastUpdateDate = LocalDate.now();
        LocalTime lastUpdateTime = LocalTime.now();
        LocalDateTime lastUpdate = LocalDateTime.of(lastUpdateDate,lastUpdateTime).truncatedTo(ChronoUnit.MINUTES);
        //LocalDateTime lastUpdateDemo = lastUpdate.format(DateTimeFormatter.ofPattern(""));
        String lastUpdatedBy = "script";
        int newDivision = 0;
        //get the proper division ID
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        try{
            String query = "SELECT * from first_level_divisions";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String oldDivision = rs.getString("Division");
                if(oldDivision.equals(selectedDivision))
                    newDivision = rs.getInt("Division_ID");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        Tables.addCustomer(new Customers(customerID,name,address,postal,phone,createDate,createdBy,lastUpdate,lastUpdatedBy,newDivision));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
    private void myAlert(String alert){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(alert);
        a.show();
    }
}