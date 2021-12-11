package controller;
/**
 * class addCustomerController.java
 */

/**
 * @author Justin Simons
 * */
import helper.DBQuery;
import helper.JDBC;
import javafx.collections.FXCollections;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * class addCustomerController adds a new customer to the database.
 */
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
    /**
     * initialize calls fillComboBoxes()
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            fillComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    ObservableList<String> allCountries = FXCollections.observableArrayList();
    ObservableList<String> allDivisions = FXCollections.observableArrayList();

    /**
     * fillComboBoxes prefills the combo boxes.
     * gets a connection to the database
     * fills the countries combo box by getting all countries from the DB.
     * fills the divisions combo box by getting all divisions from the DB.
     * @throws SQLException is called if a connection is not made.
     */
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
                allCountries.add(country);
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
                allDivisions.add(division);
                addNewCustomerDivision.getItems().add(division);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static int customerID = Tables.getAllCustomers().get(Tables.getAllCustomers().size()-1).getId();

    Stage stage;
    Parent scene;
    /**
     * addNewCustomerCancelButton closes the app
     * @param event called when cancel button is clicked.
     * @throws IOException is thrown if no scene is found.
     */
    @FXML
    void addNewCustomerCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
    /**
     * addNewCustomerDivisionClicked first checks if the country is set.
     * if country is empty, nothing happens.
     * if not, it then connects to the database.
     * it then adds only the divisions associated with the chosen country.
     * @param event called when the start time combo box is clicked
     */
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

    /**
     *addNewCustomerSubmitButton sends the new data to create a new customer.
     * first gets all the values from the the user input.
     * makes sure none of them are empty.
     * makes sure that a country and/or division name isn't present in the address.
     * changes the LocalDateTimes to UTC zone.
     * Gets the division ID from the chosen division name.
     * adds the new customer to the Tables class.
     * returns to the main page.
     * @param event called when submit button is clicked.
     * @throws IOException thrown if no scene is found
     * @throws SQLException thrown if no connection is made.
     */
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
        //make sure that the address doesn't contain a country or division name.
        boolean CorDFound = false;
        for(String countries: allCountries){
            String lowCase = address.toLowerCase(Locale.ROOT);
            if (lowCase.contains(countries.toLowerCase(Locale.ROOT))) {
                CorDFound = true;
                break;
            }
        }
        for(String divisions: allDivisions){
            String lowCase = address.toLowerCase(Locale.ROOT);
            if (lowCase.contains(divisions.toLowerCase(Locale.ROOT))) {
                CorDFound = true;
                break;
            }
        }
        if(CorDFound) {
            myAlert("Make sure the address doesn't contain a Country or Division name.");
            return;
        }
        //get new customer data
        customerID++;
        //change create date from local to UTC
        LocalDate createDateDate = LocalDate.now();
        LocalTime createDateTime = LocalTime.now();
        LocalDateTime localCreateDate = LocalDateTime.of(createDateDate,createDateTime).truncatedTo(ChronoUnit.MINUTES);
        ZonedDateTime localZonedDateTime = ZonedDateTime.of(localCreateDate, ZoneId.systemDefault());
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCCreateDate = UTCZonedDateTime.toLocalDateTime();

        String createdBy = "script";
        //change last update from local to UTC
        LocalDate lastUpdateDate = LocalDate.now();
        LocalTime lastUpdateTime = LocalTime.now();
        LocalDateTime localLastUpdate = LocalDateTime.of(lastUpdateDate,lastUpdateTime).truncatedTo(ChronoUnit.MINUTES);
        localZonedDateTime = ZonedDateTime.of(localLastUpdate, ZoneId.systemDefault());
        UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCLastUpdate = UTCZonedDateTime.toLocalDateTime();

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


        Tables.addNewCustomer(new Customers(customerID,name,address,postal,phone,UTCCreateDate,createdBy,UTCLastUpdate,lastUpdatedBy,newDivision));
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
    /**
     * myAlert shows an alert.
     * @param alert gets the string that will be presented in the alert
     */
    private void myAlert(String alert){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(alert);
        a.show();
    }
}