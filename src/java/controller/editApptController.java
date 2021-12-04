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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    void editApptCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }

    @FXML
    void editApptSubmitButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/mainPage.fxml")));
        stage.setScene(new Scene(scene,1235,558));
        stage.show();
    }
}