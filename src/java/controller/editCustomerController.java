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
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

public class editCustomerController implements Initializable {
    @FXML
    private TextField editCustomerAddress;

    @FXML
    private ComboBox<String> editCustomerCountry;

    @FXML
    private ComboBox<String> editCustomerDivision;

    @FXML
    private TextField editCustomerID;

    @FXML
    private TextField editCustomerName;

    @FXML
    private TextField editCustomerPhone;

    @FXML
    private TextField editCustomerPostal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillComboBoxes();
            selectCountryAndDivision();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Customers newCustomer = Tables.getModifyCustomer();
        editCustomerAddress.setText(newCustomer.getAddress());
        editCustomerID.setText(String.valueOf(newCustomer.getId()));
        editCustomerName.setText(newCustomer.getName());
        editCustomerPhone.setText(newCustomer.getPhone());
        editCustomerPostal.setText(newCustomer.getPostalCode());
    }

    void selectCountryAndDivision() throws SQLException{
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();

        Customers newCustomer = Tables.getModifyCustomer();
        int selectedCountryID = 0;
        int selectedDivisionID = newCustomer.getDivisionID();
        //set division
        try{
            String query = "SELECT * from first_level_divisions";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                int nextDivisionID = rs.getInt("Division_ID");
                if(nextDivisionID == selectedDivisionID){
                    selectedCountryID = rs.getInt("Country_ID");
                    editCustomerDivision.getSelectionModel().select(rs.getString("Division"));
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //set country
        try{
            String query = "SELECT * from countries";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                int nextCountryID = rs.getInt("Country_ID");
                if(nextCountryID == selectedCountryID){
                    editCustomerCountry.getSelectionModel().select(rs.getString("Country"));
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
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
                editCustomerCountry.getItems().add(country);
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
                editCustomerDivision.getItems().add(division);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

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
    void editCustomerSubmitButton(ActionEvent event) throws IOException, SQLException {
        int id = Integer.parseInt(editCustomerID.getText());
        String name = editCustomerName.getText().trim();
        String address = editCustomerAddress.getText().trim();
        String postal = editCustomerPostal.getText().trim();
        String selectedCountry = editCustomerCountry.getValue();
        String selectedDivision = editCustomerDivision.getValue();
        String phone = editCustomerPhone.getText().trim();
        LocalDateTime createDate = Tables.getModifyCustomer().getCreateDate();
        String createdBy = "script";
        LocalDate lastUpdateDate = LocalDate.now();
        LocalTime lastUpdateTime = LocalTime.now();
        LocalDateTime lastUpdate = LocalDateTime.of(lastUpdateDate,lastUpdateTime).truncatedTo(ChronoUnit.MINUTES);
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

        if( (selectedCountry == null) || (selectedDivision == null) || name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty()){
            myAlert("Please fill out each field.");
            return;
        }

        int customerIndex = 0;
        ObservableList<Customers> customers = Tables.getAllCustomers();
        for(int i = 0; i < customers.size(); i++){
            if(customers.get(i).getId() == Tables.getModifyCustomer().getId()){
                customerIndex = i;
                break;
            }
        }

        Tables.updateCustomer(customerIndex, (new Customers(id,name,address,postal,phone,createDate,createdBy,lastUpdate,lastUpdatedBy,newDivision)));

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    public void editCustomerDivisionClicked(MouseEvent mouseEvent) throws SQLException {
        String selectedCountry = editCustomerCountry.getValue();
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
            editCustomerDivision.getItems().clear();
            String query = "SELECT * from first_level_divisions";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                divisionCountryID = rs.getInt("Country_ID");
                if(divisionCountryID == selectedCountryID)
                    editCustomerDivision.getItems().add(rs.getString("Division"));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void myAlert(String alert){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(alert);
        a.show();
    }
}