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
import model.Appointments;
import model.Customers;
import model.Tables;
import model.ZoneTimes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private static int apptID = 2;

    Stage stage;
    Parent scene;

    @FXML
    void addNewApptStartTimeClicked(MouseEvent mouseEvent) {
        LocalTime selectedEndTime = addNewApptEndTime.getValue();
        if(selectedEndTime == null) {
            return;
        }
        LocalDateTime selectedEndDateTime;
        if(selectedEndTime.equals(LocalTime.MIDNIGHT) || ((selectedEndTime.isAfter(LocalTime.MIDNIGHT)) && (selectedEndTime.isBefore(localStartTimes.get(0)))))
            selectedEndDateTime = LocalDateTime.of(LocalDate.now().plusDays(1),selectedEndTime);
        else
            selectedEndDateTime = LocalDateTime.of(LocalDate.now(),selectedEndTime);

        addNewApptStartTime.getItems().clear();
        for(LocalDateTime time : localStartDateTimes){
            if(time.isBefore(selectedEndDateTime)) {
                addNewApptStartTime.getItems().add(time.toLocalTime());
            }
            else
                return;
        }
    }


    @FXML
    void addNewApptEndTimeClicked(MouseEvent mouseEvent) {
        LocalTime selectedStartTime = addNewApptStartTime.getValue();
        if(selectedStartTime == null) {
            return;
        }
        LocalDateTime selectedStartDateTime;
        if(selectedStartTime.equals(LocalTime.MIDNIGHT) || ((selectedStartTime.isAfter(LocalTime.MIDNIGHT)) && (selectedStartTime.isBefore(localStartTimes.get(0)))))
            selectedStartDateTime = LocalDateTime.of(LocalDate.now().plusDays(1),selectedStartTime);
        else
            selectedStartDateTime = LocalDateTime.of(LocalDate.now(),selectedStartTime);

        addNewApptEndTime.getItems().clear();
        boolean timesMatch = false;
        for(LocalDateTime time : localEndDateTimes){
            if(selectedStartTime.equals(LocalTime.of(13, 0)))
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


        //get the rest of the new appt data: createDate, createdBy, lastUpdate,LastUpdatedBY, contactID
        apptID++;
        LocalDate createDateDate = LocalDate.now();
        LocalTime createDateTime = LocalTime.now();
        LocalDateTime createDate = LocalDateTime.of(createDateDate,createDateTime).truncatedTo(ChronoUnit.MINUTES);
        String createdBy = "script";
        LocalDate lastUpdateDate = LocalDate.now();
        LocalTime lastUpdateTime = LocalTime.now();
        LocalDateTime lastUpdate = LocalDateTime.of(lastUpdateDate,lastUpdateTime).truncatedTo(ChronoUnit.MINUTES);
        String lastUpdatedBy = "script";
        //set the startDateTimes
        LocalDateTime startDateTime;
        if(startTime.equals(LocalTime.MIDNIGHT) || startTime.isAfter(LocalTime.MIDNIGHT) && (startTime.isBefore(localStartTimes.get(0))))
            startDateTime = LocalDateTime.of(date.plusDays(1),startTime);
        else
            startDateTime = LocalDateTime.of(date,startTime);

        //set the endDateTimes
        LocalDateTime endDateTime;
        if(endTime.equals(LocalTime.MIDNIGHT) || endTime.isAfter(LocalTime.MIDNIGHT) && (endTime.isBefore(localStartTimes.get(0))))
            endDateTime = LocalDateTime.of(date.plusDays(1),endTime);
        else
            endDateTime = LocalDateTime.of(date,endTime);

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

        Tables.addAppointment(new Appointments(apptID, title, description, location, type, startDateTime, endDateTime, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID));

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