package controller;

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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
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
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * class reportsController.java
 */

/**
 * @author Justin Simons
 * */

/**
 * reportsController class shows the reports
 * */
public class reportsController implements Initializable {

    @FXML
    private TableColumn<Appointments, Integer> reportScheduleApptID;

    @FXML
    private ComboBox<String> reportScheduleChooseContact;

    @FXML
    private TableColumn<Appointments, Integer> reportScheduleCustomerID;

    @FXML
    private TableColumn<Appointments, String> reportScheduleDescription;

    @FXML
    private TableColumn<Appointments, LocalDateTime> reportScheduleEnd;

    @FXML
    private TableColumn<Appointments, LocalDateTime> reportScheduleStart;

    @FXML
    private TableView<Appointments> reportScheduleTable;

    @FXML
    private TableColumn<Appointments,String> reportScheduleTitle;

    @FXML
    private TableColumn<Appointments, String> reportScheduleType;

    @FXML
    private Label reportsTotalApptsApr;

    @FXML
    private Label reportsTotalApptsAug;

    @FXML
    private Label reportsTotalApptsDBrief;

    @FXML
    private Label reportsTotalApptsDec;

    @FXML
    private Label reportsTotalApptsFeb;

    @FXML
    private Label reportsTotalApptsJan;

    @FXML
    private Label reportsTotalApptsJuly;

    @FXML
    private Label reportsTotalApptsJune;

    @FXML
    private Label reportsTotalApptsMar;

    @FXML
    private Label reportsTotalApptsMay;

    @FXML
    private Label reportsTotalApptsNov;

    @FXML
    private Label reportsTotalApptsOct;

    @FXML
    private Label reportsTotalApptsPlanning;

    @FXML
    private Label reportsTotalApptsSep;

    @FXML
    private Label reportsTotalApptsTrain;

    @FXML
    private Label reportsTotalCustomersCanada;

    @FXML
    private Label reportsTotalCustomersUK;

    @FXML
    private Label reportsTotalCustomersUS;

    ObservableList<String> allContacts = FXCollections.observableArrayList();

    /**
     * initialize calls SetApptReportData(),SetCustomerReportDate(), and FillContactBox();
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        SetApptReportData();
        try {
            SetCustomerReportDate();
            FillContactBox();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        SetDateTimeFormats();
    }

    /**
     * SetDateTimeFormats sets the LocalDateTime format.
     * first creates the DateTimeFormatter. then sets it to the CellVallueFactory and CellFactory for End and Start.
     **/
    private void SetDateTimeFormats(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        reportScheduleEnd.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());
        reportScheduleEnd.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.format(formatter));
            }
        });

        reportScheduleStart.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        reportScheduleStart.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.format(formatter));
            }
        });
    }

    /**
     * SetApptReportData sets all the report data.
     * first checks how many appointments are in each month
     * then checks how many appointments for each type.
     * then adds the number of months and types to each label.
     */
    void SetApptReportData(){
        ObservableList<Appointments> allAppts = Tables.getAllAppointments();
        int jan = 0; int feb = 0; int mar = 0; int apr = 0; int may = 0; int june = 0; int july = 0; int aug = 0; int sep = 0; int oct = 0; int nov = 0; int dec = 0;
        int plan = 0; int brief = 0; int train = 0;
        for(Appointments appt: allAppts){
           Month month =  appt.getStart().toLocalDate().getMonth();
            switch (month) {
                case JANUARY -> jan = jan+1;
                case FEBRUARY -> feb = feb+1;
                case MARCH -> mar = mar+1;
                case APRIL -> apr = apr+1;
                case MAY -> may = may+1;
                case JUNE -> june = june+1;
                case JULY -> july = july+1;
                case AUGUST -> aug = aug+1;
                case SEPTEMBER -> sep = sep+1;
                case OCTOBER -> oct = oct+1;
                case NOVEMBER -> nov = nov+1;
                case DECEMBER -> dec = dec+1;
            }
            String type = appt.getType();
            if(type.contains("Planning"))
                plan = plan + 1;
            if(type.contains("De-Briefing"))
                brief = brief +1;
            if(type.contains("Training"))
                train = train +1;
        }
        //set month numbers
        reportsTotalApptsJan.setText(String.valueOf(jan));
        reportsTotalApptsFeb.setText(String.valueOf(feb));
        reportsTotalApptsMar.setText(String.valueOf(mar));
        reportsTotalApptsApr.setText(String.valueOf(apr));
        reportsTotalApptsMay.setText(String.valueOf(may));
        reportsTotalApptsJune.setText(String.valueOf(june));
        reportsTotalApptsJuly.setText(String.valueOf(july));
        reportsTotalApptsAug.setText(String.valueOf(aug));
        reportsTotalApptsSep.setText(String.valueOf(sep));
        reportsTotalApptsOct.setText(String.valueOf(oct));
        reportsTotalApptsNov.setText(String.valueOf(nov));
        reportsTotalApptsDec.setText(String.valueOf(dec));
        //set Type Numbers
        reportsTotalApptsPlanning.setText(String.valueOf(plan));
        reportsTotalApptsDBrief.setText(String.valueOf(brief));
        reportsTotalApptsTrain.setText(String.valueOf(train));

    }

    /**
     * FillContactBox fills the contact box with all contacts.
     * first gets a connection to the Data Base.
     * then adds each contact to choose contact combo box.
     * @throws SQLException thrown if it can't make a connecting to the data base.
     */
    void FillContactBox() throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        try{
            String query = "SELECT * from contacts";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String contactName = rs.getString("Contact_Name");
                allContacts.add(contactName);
                reportScheduleChooseContact.getItems().add(contactName);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * SetCustomerReportDate sets the labels to show how many customers are from each country
     * first gets all customers from the tables class.
     * then makes a connection to the database
     * then goes through every first level division in the database and compares the division id to the one associated with each customer.
     * if the customer matches the division ID, then it gets the country ID from the division and increases that country by one.
     * once all customers have been called, it will have the data to show how many customers are from the US, UK, and Canada
     * @throws SQLException thrown if can't make a connection to the database
     */
    void SetCustomerReportDate() throws SQLException {
        ObservableList<Customers> allCustomers = Tables.getAllCustomers();
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        //add data to appointments table.
        int US = 0; int UK = 0; int canada = 0;
        try{
            String query = "SELECT * from first_level_divisions";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                for(Customers customer : allCustomers){
                    if(customer.getDivisionID() == divisionID){
                        if(rs.getInt("Country_ID") == 1)
                            US = US + 1;
                        else if(rs.getInt("Country_ID") == 2)
                            UK = UK + 1;
                        else if(rs.getInt("Country_ID") == 3)
                            canada = canada + 1;
                        else {
                            myAlert("No Country Found.");
                            return;
                        }
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        //set the customer country numbers
        reportsTotalCustomersUS.setText(String.valueOf(US));
        reportsTotalCustomersUK.setText(String.valueOf(UK));
        reportsTotalCustomersCanada.setText(String.valueOf(canada));
    }

    /**
     * reportScheduleChooseContactChosen takes the chosen contact and shows that contacts appointments
     * first gets the selected contact name
     * gets all appointments from tables class
     * it then goes through every appointment and adds only the appointments associated with the chosen contact
     * and adds it to the table. then refreshes the table
     * @param event called when a contact is chosen from the combo box
     */
    @FXML
    void reportScheduleChooseContactChosen(ActionEvent event) {
        String selectedContactName = reportScheduleChooseContact.getValue();
        ObservableList<Appointments> allAppts = Tables.getAllAppointments();
        ObservableList<Appointments> contactAppts = FXCollections.observableArrayList();
        int contactID = -1;
        //get contact ID
        for(int i = 0; i < allContacts.size(); i++){
            if(selectedContactName.equals(allContacts.get(i)))
                contactID = i+1;
        }
        //get the appointments for the chosen contact
        for(Appointments appt: allAppts){
            if(appt.getContactID() == contactID)
                contactAppts.add(appt);
        }
        reportScheduleTable.setItems(contactAppts);
        reportScheduleTable.refresh();
    }

    Stage stage;
    Parent scene;
    /**
     * reportApptReturnButton returns to main page
     * @param event called when return to main page button is clicked.
     * @throws IOException is thrown if no scene is found.
     */
    @FXML
    void reportApptReturnButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
    /**
     * reportCustomersReturnButton returns to main page
     * @param event called when return to main page button is clicked.
     * @throws IOException is thrown if no scene is found.
     */
    @FXML
    void reportCustomersReturnButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
    /**
     * reportScheduleReturnButton returns to main page
     * @param event called when return to main page button is clicked.
     * @throws IOException is thrown if no scene is found.
     */
    @FXML
    void reportScheduleReturnButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
    /**
     * myAlert shows an alert.
     * @param alert gets the string that will be presented in the alert
     */
    public void myAlert(String alert){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(alert);
        a.showAndWait();
    }
}