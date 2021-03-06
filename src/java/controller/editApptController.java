package controller;
/**
 * class editApptController.java
 */

/**
 * @author Justin Simons
 * */
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import model.Tables;
import model.ZoneTimes;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * class editApptController edits the appt in the database.
 */
public class editApptController implements Initializable {
    @FXML
    private TextField editApptTitle;

    @FXML
    private ComboBox<String> editApptContact;

    @FXML
    private ComboBox<Integer> editApptCustomerID;

    @FXML
    private DatePicker editApptDate;

    @FXML
    private TextField editApptDescription;

    @FXML
    private ComboBox<LocalTime> editApptEndTime;

    @FXML
    private TextField editApptID;

    @FXML
    private TextField editApptLocation;

    @FXML
    private ComboBox<LocalTime> editApptStartTime;

    @FXML
    private ComboBox<String> editApptType;

    @FXML
    private ComboBox<Integer> editApptUserID;

    ObservableList<LocalTime> localStartTimes = ZoneTimes.setLocalStartTimes();
    ObservableList<LocalTime> localEndTimes = ZoneTimes.setLocalEndTimes();
    ObservableList<LocalDateTime> localStartDateTimes = ZoneTimes.getLocalDateStartTimes();
    ObservableList<LocalDateTime> localEndDateTimes = ZoneTimes.getLocalDateEndTimes();

    /**
     * initialize calls fillComboBoxes()
     * then prefills all fields from the chosen appointment to edit.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillComboBoxes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //fill appointment information
        Appointments modifyAppt = Tables.getModifyAppt();
        editApptID.setText(String.valueOf(modifyAppt.getId()));
        editApptUserID.getSelectionModel().select(modifyAppt.getUserID() - 1);
        editApptCustomerID.getSelectionModel().select(modifyAppt.getUserID() - 1);
        editApptTitle.setText(String.valueOf(modifyAppt.getTitle()));
        editApptDescription.setText(String.valueOf(modifyAppt.getDescription()));
        editApptLocation.setText(String.valueOf(modifyAppt.getLocation()));
        editApptContact.getSelectionModel().select(modifyAppt.getContactID() - 1);
        editApptType.getSelectionModel().select(modifyAppt.getType());
        editApptDate.setValue(modifyAppt.getStart().toLocalDate());
        editApptStartTime.getSelectionModel().select(modifyAppt.getStart().toLocalTime());
        editApptEndTime.getSelectionModel().select(modifyAppt.getEnd().toLocalTime());
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
                editApptUserID.getItems().add(userID);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //fill CustomerID comboBox
        ObservableList<Customers> customerCBox = Tables.getAllCustomers();
        for(Customers customer : customerCBox){
            editApptCustomerID.getItems().add(customer.getId());
        }

        //fill Contacts combo box
        try{
            String query = "SELECT * from contacts";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String contact = rs.getString("Contact_Name");
                editApptContact.getItems().add(contact);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //fill type combo box
        editApptType.getItems().add("Planning Session");
        editApptType.getItems().add("De-Briefing");
        editApptType.getItems().add("Training");

        //fill start time combo box with the local time versions of the business hours.8:00 a.m. to 10:00 p.m. EST
        editApptStartTime.getItems().addAll(localStartTimes);

        // fill end time combo box.
        editApptEndTime.getItems().addAll(localEndTimes);

        //disable weekends and all previous days on datepicker
        editApptDate.setDayCellFactory(picker -> new DateCell() {
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

    Stage stage;
    Parent scene;

    /**
     * editApptStartTimeClicked first checks if the end time is set.
     * if end time is set then its cleared. if not nothing happens
     * @param mouseEvent called when the start time combo box is clicked
     */
    @FXML
    void editApptStartTimeClicked(MouseEvent mouseEvent) {
        LocalTime selectedEndTime = editApptEndTime.getValue();
        if(selectedEndTime == null) {
            return;
        }
        editApptEndTime.getSelectionModel().clearSelection();
    }
    /**
     * editApptEndTimeClicked first checks if the start time is set.
     * if start time is empty, nothing happens.
     * if it's not empty, then it checks to see if end time after midnight.
     * if it is, it adds a day date and sets the local start date time.
     * if it's not after midnight, then is just sets the local start date time.
     * it then clears all times from the end times.
     * it then adds all end times that are after the selected start time.
     * @param mouseEvent called when the start time combo box is clicked
     */
    @FXML
    void editApptEndTimeClicked(MouseEvent mouseEvent) {
        LocalTime selectedStartTime = editApptStartTime.getValue();
        if(selectedStartTime == null)
            return;

        LocalDateTime selectedStartDateTime;
        if(selectedStartTime.equals(LocalTime.MIDNIGHT) || ((selectedStartTime.isAfter(LocalTime.MIDNIGHT)) && (selectedStartTime.isBefore(localStartTimes.get(0)))))
            selectedStartDateTime = LocalDateTime.of(LocalDate.now().plusDays(1),selectedStartTime);
        else
            selectedStartDateTime = LocalDateTime.of(LocalDate.now(),selectedStartTime);

        if(localEndDateTimes.get(0).toLocalTime().minusMinutes(15).equals(selectedStartTime.plusHours(1)))
            return;
        editApptEndTime.getItems().clear();
        boolean timesMatch = false;
        for(LocalDateTime time : localEndDateTimes){
            if(selectedStartTime.equals(localStartTimes.get(0)))
                timesMatch = true;
            if(timesMatch) {
                if (time.isAfter(selectedStartDateTime)) {
                    editApptEndTime.getItems().add(time.toLocalTime());
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
    void editApptCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
    /**
     *editApptSubmitButton sends the new data to create a new appt.
     * first gets all the values from the the user input.
     * makes sure none of them are empty.
     * changes the LocalDateTimes to UTC zone.
     * makes sure the times are within the business hours.
     * makes sure the times don't overlap with preexisting appointments.
     * Gets the contact ID from the chosen contact name.
     * gets the chosen appt and changes the info to the new data and adds it to the Tables class.
     * returns to the main page.
     * @param event called when submit button is clicked.
     * @throws IOException thrown if no scene is found
     * @throws SQLException thrown if no connection is made.
     */
    @FXML
    void editApptSubmitButton(ActionEvent event) throws IOException, SQLException {
        int apptID = Tables.getModifyAppt().getId();
        Integer userID = editApptUserID.getValue();
        Integer customerID = editApptCustomerID.getValue();
        String title = editApptTitle.getText().trim();
        String description = editApptDescription.getText().trim();
        String location = editApptLocation.getText().trim();
        String selectedContactName = editApptContact.getValue();
        String type = editApptType.getValue();
        LocalDate date = editApptDate.getValue();
        LocalTime startTime = editApptStartTime.getValue();
        LocalTime endTime = editApptEndTime.getValue();

        //make sure each field is filled in
        if((userID == null) || (customerID == null) || (title.isEmpty()) || (description.isEmpty()) || (location.isEmpty()) || (selectedContactName == null) || (type == null) || (date == null) || (startTime == null) || (endTime == null) )  {
            myAlert("Please fill out each field.");
            return;
        }

        //get the rest of the updated appt data: createDate, createdBy, lastUpdate,LastUpdatedBY, contactIDf
        LocalDateTime localCreateDate = Tables.getModifyAppt().getCreateDate();
        ZonedDateTime localZonedDateTime = ZonedDateTime.of(localCreateDate, ZoneId.systemDefault());
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCCreateDate = UTCZonedDateTime.toLocalDateTime();
        String createdBy = "script";
        LocalDate lastUpdateDate = LocalDate.now();
        LocalTime lastUpdateTime = LocalTime.now();
        LocalDateTime localLastUpdate = LocalDateTime.of(lastUpdateDate,lastUpdateTime).truncatedTo(ChronoUnit.MINUTES);
        localZonedDateTime = ZonedDateTime.of(localLastUpdate, ZoneId.systemDefault());
        UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCLastUpdate = UTCZonedDateTime.toLocalDateTime();
        String lastUpdatedBy = "script";
        //set the startDateTimes
        LocalDateTime localStartDateTime = LocalDateTime.of(date,startTime);
        /*
        if(startTime.equals(LocalTime.MIDNIGHT) || startTime.isAfter(LocalTime.MIDNIGHT) && (startTime.isBefore(localEndTimes.get(localEndTimes.size()-1))))
            localStartDateTime = LocalDateTime.of(date.plusDays(1),startTime);
        else
            localStartDateTime = LocalDateTime.of(date,startTime);

         */
        localZonedDateTime = ZonedDateTime.of(localStartDateTime, ZoneId.systemDefault());
        UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCStartDateTime = UTCZonedDateTime.toLocalDateTime();
        //set the endDateTimes
        LocalDateTime localEndDateTime;
        if((endTime.equals(LocalTime.MIDNIGHT) || endTime.isAfter(LocalTime.MIDNIGHT) && (endTime.isBefore(localEndTimes.get(localEndTimes.size()-1).plusMinutes(15)))) && !(startTime.equals(LocalTime.MIDNIGHT) || startTime.isAfter(LocalTime.MIDNIGHT) && (startTime.isBefore(localEndTimes.get(localEndTimes.size()-1)))))
            localEndDateTime = LocalDateTime.of(date.plusDays(1),endTime);
        else
            localEndDateTime = LocalDateTime.of(date,endTime);
        localZonedDateTime = ZonedDateTime.of(localEndDateTime, ZoneId.systemDefault());
        UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCEndDateTime = UTCZonedDateTime.toLocalDateTime();
        //alert if times are not within the business hours
        LocalTime startOfBH = localStartTimes.get(0);
        LocalTime endOfBH = localEndTimes.get(localEndTimes.size()-1);
        boolean outOfBH = false;
        if(localStartTimes.contains(LocalTime.MIDNIGHT)){
            if((startTime.isBefore(startOfBH) && startTime.isAfter(endOfBH)) ||(endTime.isBefore(startOfBH) && endTime.isAfter(endOfBH)) ){
                outOfBH = true;
            }
        }
        else{
            if(!(startTime.isAfter(startOfBH) && startTime.isBefore(endOfBH)) || !(endTime.isAfter(startOfBH) && endTime.isBefore(endOfBH)) )
                outOfBH = true;
        }

        if(outOfBH){
            myAlert("The start or end time is not within the buisness hours of "+ localStartTimes.get(0) + " and " + localEndTimes.get(localEndTimes.size()-1));
            return;
        }
        //alert if the selected time overlaps a preexisting appointment
        ObservableList<Appointments> allAppts = Tables.getAllAppointments();
        boolean overLappedFound = false;
        StringBuilder alertString = new StringBuilder();
        for(Appointments appt: allAppts){
            if(appt.getId() == apptID)
                continue;
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
            myAlert("The following appointment(s) are overlapping with this new appointment: " + alertString);
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

        int apptIndex = 0;
        ObservableList<Appointments> appt = Tables.getAllAppointments();
        for(int i = 0; i < appt.size(); i++){
            if(appt.get(i).getId() == Tables.getModifyAppt().getId()){
                apptIndex = i;
                break;
            }
        }

        Tables.updateAppt(apptIndex, (new Appointments(apptID, title, description, location, type, UTCStartDateTime, UTCEndDateTime, UTCCreateDate, createdBy, UTCLastUpdate, lastUpdatedBy, customerID, userID, contactID)));

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