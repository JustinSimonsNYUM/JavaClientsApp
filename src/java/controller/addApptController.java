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

    @FXML
    void addNewApptStartTimeClicked(MouseEvent mouseEvent) {
        LocalTime selectedEndTime = addNewApptEndTime.getValue();
        if(selectedEndTime == null) {
            return;
        }
        addNewApptEndTime.getSelectionModel().clearSelection();
    }


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


    @FXML
    void addNewApptCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

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


//**************************************CHANGE THE TIMES FROM LOCAL TO UTC**********************
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
        if(startTime.equals(LocalTime.MIDNIGHT) || startTime.isAfter(LocalTime.MIDNIGHT) && (startTime.isBefore(localStartTimes.get(0))))
            localStartDateTime = LocalDateTime.of(date.plusDays(1),startTime);
        else
            localStartDateTime = LocalDateTime.of(date,startTime);
        ZonedDateTime localStartZoned = ZonedDateTime.of(localStartDateTime, ZoneId.systemDefault());
        ZonedDateTime UTCStartZonedDateTime = ZonedDateTime.ofInstant(localStartZoned.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCStartDateTime =  UTCStartZonedDateTime.toLocalDateTime();

        //set the endDateTimes and change to UTC
        LocalDateTime localEndDateTime;
        if(endTime.equals(LocalTime.MIDNIGHT) || endTime.isAfter(LocalTime.MIDNIGHT) && (endTime.isBefore(localStartTimes.get(0))))
            localEndDateTime = LocalDateTime.of(date.plusDays(1),endTime);
        else
            localEndDateTime = LocalDateTime.of(date,endTime);
        ZonedDateTime localEndZoned = ZonedDateTime.of(localEndDateTime, ZoneId.systemDefault());
        ZonedDateTime UTCEndZonedDateTime = ZonedDateTime.ofInstant(localEndZoned.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCEndDateTime =  UTCEndZonedDateTime.toLocalDateTime();
//*****************FIGURE OUT OUT OF BUISSNESS HOURS ALERTS****************
        boolean outOfBusinessHours = false;
        if(startTime.isAfter(localStartTimes.get(0).minusMinutes(1)) && (endTime.isBefore(localEndTimes.get(localEndTimes.size()-1).plusMinutes(1)))){
            outOfBusinessHours = true;
        }
        if(outOfBusinessHours){
            myAlert("The start or end time is not within the buisness hours of "+ localStartTimes.get(0) + " and " + localEndTimes.get(localEndTimes.size()-1));
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