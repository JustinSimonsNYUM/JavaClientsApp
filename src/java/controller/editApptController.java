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

    @FXML
    void editApptStartTimeClicked(MouseEvent mouseEvent) {
        LocalTime selectedEndTime = editApptEndTime.getValue();
        if(selectedEndTime == null) {
            return;
        }
        editApptEndTime.getSelectionModel().clearSelection();
    }

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


    @FXML
    void editApptCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

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
        boolean outOfBusinessHours = false;
        if(startTime.isAfter(localStartTimes.get(0).minusMinutes(1)) && (endTime.isBefore(localEndTimes.get(localEndTimes.size()-1).plusMinutes(1)))){
            outOfBusinessHours = true;
        }
        if(outOfBusinessHours){
            myAlert("The start or end time is not within the buisness hours of "+ localStartTimes.get(0) + " and " + localEndTimes.get(localEndTimes.size()-1));
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
        LocalDateTime localStartDateTime;
        if(startTime.equals(LocalTime.MIDNIGHT) || startTime.isAfter(LocalTime.MIDNIGHT) && (startTime.isBefore(localStartTimes.get(0))))
            localStartDateTime = LocalDateTime.of(date.plusDays(1),startTime);
        else
            localStartDateTime = LocalDateTime.of(date,startTime);
        localZonedDateTime = ZonedDateTime.of(localStartDateTime, ZoneId.systemDefault());
        UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCStartDateTime = UTCZonedDateTime.toLocalDateTime();
        //set the endDateTimes
        LocalDateTime localEndDateTime;
        if(endTime.equals(LocalTime.MIDNIGHT) || endTime.isAfter(LocalTime.MIDNIGHT) && (endTime.isBefore(localStartTimes.get(0))))
            localEndDateTime = LocalDateTime.of(date.plusDays(1),endTime);
        else
            localEndDateTime = LocalDateTime.of(date,endTime);
        localZonedDateTime = ZonedDateTime.of(localEndDateTime, ZoneId.systemDefault());
        UTCZonedDateTime = ZonedDateTime.ofInstant(localZonedDateTime.toInstant(), ZoneId.of("UTC"));
        LocalDateTime UTCEndDateTime = UTCZonedDateTime.toLocalDateTime();

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
    private void myAlert(String alert){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(alert);
        a.show();
    }
}