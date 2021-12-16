package controller;
/**
 * class editCustomerController.java
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
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * class editCustomerController adds a new customer to the database.
 */
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
    /**
     * initialize calls fillComboBoxes()
     * calls selectCountryAndDivision()
     * then prefills all fields from the chosen appointment to edit.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillComboBoxes();
            selectCountryAndDivision();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Customers modifyCustomer = Tables.getModifyCustomer();
        editCustomerAddress.setText(modifyCustomer.getAddress());
        editCustomerID.setText(String.valueOf(modifyCustomer.getId()));
        editCustomerName.setText(modifyCustomer.getName());
        editCustomerPhone.setText(modifyCustomer.getPhone());
        editCustomerPostal.setText(modifyCustomer.getPostalCode());
    }

    ObservableList<String> allCountries = FXCollections.observableArrayList();
    ObservableList<String> allDivisions = FXCollections.observableArrayList();

    /**
     * selectCountryAndDivision first gets a connection to the database
     * it then gets the division name by comaring it to the chosen customer division ID and presets the division.
     * then pre sets the country name based of the division ID
     * @throws SQLException if can't get a connection
     */
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
                allDivisions.add(division);
                editCustomerDivision.getItems().add(division);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    Stage stage;
    Parent scene;
    /**
     * editCustomerCancelButton closes the app
     * @param event called when cancel button is clicked.
     * @throws IOException is thrown if no scene is found.
     */
    @FXML
    void editCustomerCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
    /**
     * editCustomerDivisionClicked first checks if the country is set.
     * if country is empty, nothing happens.
     * if not, it then connects to the database.
     * it then adds only the divisions associated with the chosen country.
     * @param mouseEvent called when the start time combo box is clicked
     * @throws SQLException is thrown if no connection is made.
     */
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

    /**
     * editCustomerSubmitButton sends the updated data to replace the chosen customer.
     * first gets all the values from the the user input.
     * makes sure none of them are empty.
     * makes sure that a country and/or division name isn't present in the address.
     * changes the LocalDateTimes to UTC zone.
     * Gets the division ID from the chosen division name.
     * adds the new data in place of the chosen customer to the Tables class.
     * returns to the main page.
     * @param event called when submit button is clicked.
     * @throws IOException thrown if no scene is found
     * @throws SQLException thrown if no connection is made.
     */
    @FXML
    void editCustomerSubmitButton(ActionEvent event) throws IOException, SQLException {
        int id = Integer.parseInt(editCustomerID.getText());
        String name = editCustomerName.getText().trim();
        String address = editCustomerAddress.getText().trim();
        String postal = editCustomerPostal.getText().trim();
        String selectedCountry = editCustomerCountry.getValue();
        String selectedDivision = editCustomerDivision.getValue();
        String phone = editCustomerPhone.getText().trim();
        // change create date from local to UTC
        LocalDateTime localCreateDate = Tables.getModifyCustomer().getCreateDate();
        ZonedDateTime localZonedDateTime = ZonedDateTime.of(localCreateDate, ZoneId.systemDefault());
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCCreateDate = UTCZonedDateTime.toLocalDateTime();

        String createdBy = "script";
        // change last update from local to UTC
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
        int customerIndex = 0;
        ObservableList<Customers> customers = Tables.getAllCustomers();
        for(int i = 0; i < customers.size(); i++){
            if(customers.get(i).getId() == Tables.getModifyCustomer().getId()){
                customerIndex = i;
                break;
            }
        }

        Tables.updateCustomer(customerIndex, (new Customers(id,name,address,postal,phone,UTCCreateDate,createdBy,UTCLastUpdate,lastUpdatedBy,newDivision)));

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