package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import model.Tables;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.ResourceBundle;

public class mainPageController implements Initializable {
    @FXML
    private ToggleGroup apptRadio;

    @FXML
    private TableColumn<Appointments, Integer> ATableContactID;

    @FXML
    private TableColumn<Appointments, LocalDateTime> ATableCreateDate;

    @FXML
    private TableColumn<Appointments, String> ATableCreatedBy;

    @FXML
    private TableColumn<Appointments, Integer> ATableCustomerID;

    @FXML
    private TableColumn<Appointments, String> ATableDescription;

    @FXML
    private TableColumn<Appointments, LocalDateTime> ATableEnd;

    @FXML
    private TableColumn<Appointments, Integer> ATableID;

    @FXML
    private TableColumn<Appointments, LocalDateTime> ATableLastUpdate;

    @FXML
    private TableColumn<Appointments, String> ATableLastUpdatedBy;

    @FXML
    private TableColumn<Appointments, String> ATableLocation;

    @FXML
    private TableColumn<Appointments, LocalDateTime> ATableStart;

    @FXML
    private TableColumn<Appointments, String> ATableTitle;

    @FXML
    private TableColumn<Appointments, String> ATableType;

    @FXML
    private TableColumn<Appointments, Integer> ATableUserID;

    @FXML
    private TableColumn<Customers, String> CTableAddress;

    @FXML
    private TableColumn<Customers, LocalDateTime> CTableCreateDate;

    @FXML
    private TableColumn<Customers, String> CTableCreatedBy;

    @FXML
    private TableColumn<Customers, Integer> CTableID;

    @FXML
    private TableColumn<Customers, LocalDateTime> CTableLastUpdate;

    @FXML
    private TableColumn<Customers, String> CTableLastUpdatedBy;

    @FXML
    private TableColumn<Customers, String> CTableName;

    @FXML
    private TableColumn<Customers, String> CTablePostal;

    @FXML
    private TableColumn<Customers, Integer> CTableDivisionID;

    @FXML
    private Button addApptButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private TableView<Appointments> apptTable;

    @FXML
    private TableView<Customers> customerTable;

    @FXML
    private Button deleteApptButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button editApptButton;

    @FXML
    private Button editCustomerButton;

    @FXML
    private Button goToReportsButton;

    @FXML
    private AnchorPane mainPageAnchor;

    private ObservableList<Appointments> apptList = FXCollections.observableArrayList();
    private ObservableList<Customers> customerList = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle){
        CreateApptTable();
        CreateCustomerTable();
        SetDateTimeFormats();
    }
    private void CreateApptTable() {
        apptTable.setItems(apptList);
        apptList.setAll(Tables.getAllAppointments());
        apptTable.refresh();
    }

    private void CreateCustomerTable() {
        customerTable.setItems(customerList);
        customerList.setAll(Tables.getAllCustomers());
        customerTable.refresh();
    }

    private void SetDateTimeFormats(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

        ATableCreateDate.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());
        ATableCreateDate.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.format(formatter));
            }
        });

        ATableEnd.setCellValueFactory(cellData -> cellData.getValue().endProperty());
        ATableEnd.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.format(formatter));
            }
        });

        ATableLastUpdate.setCellValueFactory(cellData -> cellData.getValue().lastUpdateProperty());
        ATableLastUpdate.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.format(formatter));
            }
        });

        ATableStart.setCellValueFactory(cellData -> cellData.getValue().startProperty());
        ATableStart.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.format(formatter));
            }
        });

        CTableCreateDate.setCellValueFactory(cellData -> cellData.getValue().createDateProperty());
        CTableCreateDate.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(item.format(formatter));
            }
        });

        CTableLastUpdate.setCellValueFactory(cellData -> cellData.getValue().lastUpdateProperty());
        CTableLastUpdate.setCellFactory(col -> new TableCell<>() {
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

    Stage stage;
    Parent scene;

    @FXML
    void apptAllRadio(ActionEvent event) {
        CreateApptTable();
    }
    @FXML
    void apptMonthRadio(ActionEvent event) {
        ObservableList<Appointments> monthAppts = FXCollections.observableArrayList();
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(LocalDate.EPOCH.lengthOfMonth());

        LocalDate nextAppt;
        for(Appointments appt : apptList){
            nextAppt = appt.getStart().toLocalDate();
            if((nextAppt.isAfter(firstDayOfMonth) || nextAppt.equals(firstDayOfMonth)) && (nextAppt.isBefore(lastDayOfMonth) || nextAppt.equals(lastDayOfMonth))){
                monthAppts.add(appt);
            }
        }
        apptTable.setItems(monthAppts);
        apptList.setAll(Tables.getAllAppointments());
        apptTable.refresh();
    }

    @FXML
    void apptWeekRadio(ActionEvent event) {
        ObservableList<Appointments> weekAppts = FXCollections.observableArrayList();
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        int subtractDays = 0;
        int addDays = 0;
        switch (today) {
            case MONDAY -> {
                subtractDays = 1;
                addDays = 5;
            }
            case TUESDAY -> {
                subtractDays = 2;
                addDays = 4;
            }
            case WEDNESDAY -> {
                subtractDays = 3;
                addDays = 3;
            }
            case THURSDAY -> {
                subtractDays = 4;
                addDays = 2;
            }
            case FRIDAY -> {
                subtractDays = 5;
                addDays = 1;
            }
        }

        LocalDate firstDayOfWeek = LocalDate.now().minusDays(subtractDays);
        LocalDate lastDayOfWeek = LocalDate.now().plusDays(addDays);

        LocalDate nextAppt;
        for(Appointments appt : apptList){
            nextAppt = appt.getStart().toLocalDate();
            if(nextAppt.isAfter(firstDayOfWeek) && nextAppt.isBefore(lastDayOfWeek)){
                weekAppts.add(appt);
            }
        }
        apptTable.setItems(weekAppts);
        apptList.setAll(Tables.getAllAppointments());
        apptTable.refresh();
    }

    @FXML
    void addApptButtonAction(ActionEvent event) {
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/addAppt.fxml")));
            stage.setScene(new Scene(scene, 600, 504));
            stage.show();
        }
        catch(Exception e){
            myAlert(Alert.AlertType.ERROR,e.getMessage() + " closing program");
            System.exit(0);
        }
    }

    @FXML
    void addCustomerButtonAction(ActionEvent event) {
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/addCustomer.fxml")));
            stage.setScene(new Scene(scene, 600, 354));
            stage.show();
        }
        catch(Exception e){
            myAlert(Alert.AlertType.ERROR,e.getMessage() + " closing program");
            System.exit(0);
        }
    }

    @FXML
    void deleteApptButtonAction(ActionEvent event) throws SQLException {
        Appointments selectedAppt = apptTable.getSelectionModel().getSelectedItem();
        if(selectedAppt == null){
            myAlert(Alert.AlertType.ERROR,"Either no appointment was found in database or no customer is selected.");
            return;
        }

        boolean apptBool = Tables.deleteAppointment(selectedAppt);
        if(apptBool) {
            apptList.setAll(Tables.getAllAppointments());
            apptTable.setItems(apptList);
            apptTable.refresh();
            myAlert(Alert.AlertType.INFORMATION,"The appointment with ID: " + selectedAppt.getId() + ", and title: " + selectedAppt.getTitle() + ", has been deleted.");
        }

    }

    @FXML
    void deleteCustomerButtonAction(ActionEvent event) throws SQLException {
        Customers selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            myAlert(Alert.AlertType.ERROR,"Either no customer was found in database or no customer is selected.");
            return;
        }
        boolean customerBool = Tables.deleteCustomer(selectedCustomer);
        if(customerBool) {
            customerList.remove(selectedCustomer);
            customerTable.setItems(customerList);
            customerTable.refresh();
            apptList.setAll(Tables.getAllAppointments());
            apptTable.setItems(apptList);
            apptTable.refresh();
            myAlert(Alert.AlertType.CONFIRMATION,selectedCustomer.getName() + "has been deleted along with their appointments.");
        }
    }

    @FXML
    void editApptButtonAction(ActionEvent event)  {
        Appointments modifyAppt = apptTable.getSelectionModel().getSelectedItem();
        if (modifyAppt == null)
            return;
        Tables.setModifyAppt(modifyAppt);
        try{
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/editAppt.fxml")));
            stage.setScene(new Scene(scene,600,504));
            stage.show();
        }
        catch(Exception e){
            myAlert(Alert.AlertType.ERROR,e.getMessage() + " closing program");
            System.exit(0);
        }
    }

    @FXML
    void editCustomerButtonAction(ActionEvent event)  {
        Customers modifyCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (modifyCustomer == null)
            return;
        Tables.setModifyCustomer(modifyCustomer);
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/editCustomer.fxml")));
            stage.setScene(new Scene(scene, 600, 354));
            stage.show();
        }
        catch(Exception e){
            myAlert(Alert.AlertType.ERROR,e.getMessage() + " closing program");
            System.exit(0);
        }
    }

    @FXML
    void goToReportsButtonAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/reports.fxml")));
        stage.setScene(new Scene(scene,600,400));
        stage.show();
    }
    @FXML
    void closeAppButton(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void myAlert(Alert.AlertType alertType, String alert){
        Alert a = new Alert(alertType);
        a.setContentText(alert);
        a.showAndWait();
    }

}
