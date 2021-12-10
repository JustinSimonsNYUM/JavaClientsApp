package controller;
/**
 * class addApptController.java
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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointments;
import model.Customers;
import model.Tables;
import model.ZoneTimes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * class addApptController adds a new appt to the database.
 */
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
    private ComboBox<LocalTime> addNewApptEndTime;

    @FXML
    private TextField addNewApptID;

    @FXML
    private TextField addNewApptLocation;

    @FXML
    private ComboBox<LocalTime> addNewApptStartTime;

    @FXML
    private TextField addNewApptTitle;

    @FXML
    private ComboBox<String> addNewApptType;

    @FXML
    private ComboBox<Integer> addNewApptUserID;

    ObservableList<LocalTime> localStartTimes = ZoneTimes.setLocalStartTimes();
    ObservableList<LocalTime> localEndTimes = ZoneTimes.setLocalEndTimes();
    ObservableList<LocalDateTime> localStartDateTimes = ZoneTimes.getLocalDateStartTimes();
    ObservableList<LocalDateTime> localEndDateTimes = ZoneTimes.getLocalDateEndTimes();

    /**
     * initialize calls fillComboBoxes()
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * fillComboBoxes prefills the combo boxes.
     * gets a connection to the database
     * fills the userId combo box by getting all users from the DB.
     * fills the customerID combo box by getting all customers from the DB.
     * fills the contacts combo box by getting all contacts from the DB.
     * fills the type combo box.
     * fills the start and end combo boxes.
     * disables the weekends and previous dates from the date picker.
     * @throws SQLException is called if a connection is not made.
     */
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

        //fill start time combo box with the local time versions of the business hours.8:00 a.m. to 10:00 p.m. EST
        addNewApptStartTime.getItems().addAll(localStartTimes);

        // fill end time combo box.
        addNewApptEndTime.getItems().addAll(localEndTimes);

        //disable weekends and all previous days on datepicker
        addNewApptDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );

                if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    setDisable(true);
                    setStyle("-fx-background-color: #808080;");
                }
            }
        });

    }

    private static int apptID = Tables.getAllAppointments().get(Tables.getAllAppointments().size()-1).getId();

    Stage stage;
    Parent scene;

    /**
     * addNewApptStartTimeClicked first checks if the end time is set.
     * if end time is set then its cleared. if not nothing happens
     * @param mouseEvent called when the start time combo box is clicked
     */
    @FXML
    void addNewApptStartTimeClicked(MouseEvent mouseEvent) {
        LocalTime selectedEndTime = addNewApptEndTime.getValue();
        if(selectedEndTime == null) {
            return;
        }
        addNewApptEndTime.getSelectionModel().clearSelection();
    }
    /**
     * addNewApptEndTimeClicked first checks if the start time is set.
     * if start time is empty, nothing happens.
     * if it's not empty, then it checks to see if end time after midnight.
     * if it is, it adds a day date and sets the local start date time.
     * if it's not after midnight, then is just sets the local start date time.
     * it then clears all times from the end times.
     * it then adds all end times that are after the selected start time.
     * @param mouseEvent called when the start time combo box is clicked
     */

    @FXML
    void addNewApptEndTimeClicked(MouseEvent mouseEvent) {
        LocalTime selectedStartTime = addNewApptStartTime.getValue();
        if(selectedStartTime == null)
            return;

        LocalDateTime selectedStartDateTime;
        if(selectedStartTime.equals(LocalTime.MIDNIGHT) || ((selectedStartTime.isAfter(LocalTime.MIDNIGHT)) && (selectedStartTime.isBefore(localStartTimes.get(0)))))
            selectedStartDateTime = LocalDateTime.of(LocalDate.now().plusDays(1),selectedStartTime);
        else
            selectedStartDateTime = LocalDateTime.of(LocalDate.now(),selectedStartTime);

        addNewApptEndTime.getItems().clear();
        boolean timesMatch = false;
        for(LocalDateTime time : localEndDateTimes){
            if(selectedStartTime.equals(localStartTimes.get(0)))
                timesMatch = true;
            if(timesMatch) {
                if (time.isAfter(selectedStartDateTime)) {
                    addNewApptEndTime.getItems().add(time.toLocalTime());
                } else
                    return;
            }
            if(time.equals(selectedStartDateTime))
                timesMatch = true;
        }
    }

    /**
     * addNewApptCancelButton closes the app
     * @param event called when cancel button is clicked.
     * @throws IOException is thrown if no scene is found.
     */
    @FXML
    void addNewApptCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    /**
     *
     * @param event called when submit button is clicked.
     * @throws IOException thrown if no scene is found
     * @throws SQLException thrown if no connection is made.
     */
    @FXML
    void addNewApptSubmitButton(ActionEvent event) throws IOException, SQLException {
        Integer userID = addNewApptUserID.getValue();
        Integer customerID = addNewApptCustomerID.getValue();
        String title = addNewApptTitle.getText().trim();
        String description = addNewApptDescription.getText().trim();
        String location = addNewApptLocation.getText().trim();
        String selectedContactName = addNewApptContact.getValue();
        String type = addNewApptType.getValue();
        LocalDate date = addNewApptDate.getValue();
        LocalTime startTime = addNewApptStartTime.getValue();
        LocalTime endTime = addNewApptEndTime.getValue();

        //make sure each field is filled in
        if((userID == null) || (customerID == null) || (title.isEmpty()) || (description.isEmpty()) || (location.isEmpty()) || (selectedContactName == null) || (type == null) || (date == null) || (startTime == null) || (endTime == null) )  {
            myAlert("Please fill out each field.");
            return;
        }

        //get the rest of the new appt data: createDate, createdBy, lastUpdate,LastUpdatedBY, contactID
        apptID++;
        //change create date from local to UTC
        LocalDate createDateDate = LocalDate.now();
        LocalTime createDateTime = LocalTime.now();
        LocalDateTime localCreateDate = LocalDateTime.of(createDateDate,createDateTime).truncatedTo(ChronoUnit.MINUTES);
        ZonedDateTime localZonedDateTime = ZonedDateTime.of(localCreateDate, ZoneId.systemDefault());
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCCreateDate = UTCZonedDateTime.toLocalDateTime();

        String createdBy = "script";
        //change last Update from local to UTC
        LocalDate lastUpdateDate = LocalDate.now();
        LocalTime lastUpdateTime = LocalTime.now();
        LocalDateTime localLastUpdate = LocalDateTime.of(lastUpdateDate,lastUpdateTime).truncatedTo(ChronoUnit.MINUTES);
        ZonedDateTime localLastUpdateZoned = ZonedDateTime.of(localLastUpdate, ZoneId.systemDefault());
        ZonedDateTime UTCLastUpdateZonedDateTime = ZonedDateTime.ofInstant(localLastUpdateZoned.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCLastUpdate = UTCLastUpdateZonedDateTime.toLocalDateTime();

        // set lastupdatedby to script
        String lastUpdatedBy = "script";

        //set the startDateTimes and change to UTC
        LocalDateTime localStartDateTime;
        if(startTime.equals(LocalTime.MIDNIGHT) || startTime.isAfter(LocalTime.MIDNIGHT) && (startTime.isBefore(localEndTimes.get(localEndTimes.size()-1))))
            localStartDateTime = LocalDateTime.of(date.plusDays(1), startTime);
        else
            localStartDateTime = LocalDateTime.of(date,startTime);

        ZonedDateTime localStartZoned = ZonedDateTime.of(localStartDateTime, ZoneId.systemDefault());
        ZonedDateTime UTCStartZonedDateTime = ZonedDateTime.ofInstant(localStartZoned.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCStartDateTime =  UTCStartZonedDateTime.toLocalDateTime();

        //set the endDateTimes and change to UTC
        LocalDateTime localEndDateTime;
        if(endTime.equals(LocalTime.MIDNIGHT) || endTime.isAfter(LocalTime.MIDNIGHT) && (endTime.isBefore(localEndTimes.get(localEndTimes.size()-1).plusMinutes(15))))
            localEndDateTime = LocalDateTime.of(date.plusDays(1), endTime);
        else
            localEndDateTime = LocalDateTime.of(date,endTime);
        ZonedDateTime localEndZoned = ZonedDateTime.of(localEndDateTime, ZoneId.systemDefault());
        ZonedDateTime UTCEndZonedDateTime = ZonedDateTime.ofInstant(localEndZoned.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCEndDateTime =  UTCEndZonedDateTime.toLocalDateTime();
        //alert if times are not within the business hours
        LocalDateTime startOfBH = LocalDateTime.of(date,localStartTimes.get(0).minusMinutes(5));
        LocalDateTime endOfBH = LocalDateTime.of(date.plusDays(1),localStartTimes.get(localEndTimes.size() - 1).plusMinutes(15));
        if(localStartDateTime.isBefore(startOfBH) || localEndDateTime.isAfter(endOfBH)){
            myAlert("The start or end time is not within the buisness hours of "+ localStartTimes.get(0) + " and " + localEndTimes.get(localEndTimes.size()-1));
            return;
        }
        //alert if the selected time overlaps a preexisting appointment
        ObservableList<Appointments> allAppts = Tables.getAllAppointments();
        boolean overLappedFound = false;
        StringBuilder alertString = new StringBuilder();
        for(Appointments appt: allAppts){
            LocalDateTime oldApptStart = appt.getStart();
            LocalDateTime oldApptEnd = appt.getEnd();
            if ((localStartDateTime.isAfter(oldApptStart.minusMinutes(5)) && localStartDateTime.isBefore(oldApptEnd.plusMinutes(5))) || (localEndDateTime.isAfter(oldApptStart.minusMinutes(5)) && localEndDateTime.isBefore(oldApptEnd.plusMinutes(5))  )) {
                alertString.append("\nAppointment with ID: ").append(appt.getId()).append(".\n       Start time: ").append(appt.getStart().toLocalDate()).append(" ").append(appt.getStart().toLocalTime()).append(".\n       End time: ").append(appt.getEnd().toLocalDate()).append(" ").append(appt.getEnd().toLocalTime()).append(".");
                overLappedFound = true;
            }
            if(oldApptStart.isAfter(localStartDateTime) && oldApptEnd.isBefore(localEndDateTime)){
                alertString.append("\nAppointment with ID: ").append(appt.getId()).append(".\n       Start time: ").append(appt.getStart().toLocalDate()).append(" ").append(appt.getStart().toLocalTime()).append(".\n       End time: ").append(appt.getEnd().toLocalDate()).append(" ").append(appt.getEnd().toLocalTime()).append(".");
                overLappedFound = true;
            }
        }
        if(overLappedFound) {
            myAlert("The following appointment(s) are overlapping with this new appointment:  " + alertString);
            return;
        }

        int contactID = 0;

        //get the proper division ID
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        try{
            String query = "SELECT * from contacts";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String nextContactName = rs.getString("Contact_Name");
                if(nextContactName.equals(selectedContactName))
                    contactID = rs.getInt("Contact_ID");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Tables.addNewAppointment(new Appointments(apptID, title, description, location, type, UTCStartDateTime, UTCEndDateTime, UTCCreateDate, createdBy, UTCLastUpdate, lastUpdatedBy, customerID, userID, contactID));

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