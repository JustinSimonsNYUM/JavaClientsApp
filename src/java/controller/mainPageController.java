package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.appointments;
import model.customers;
import model.tables;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class mainPageController implements Initializable {
    @FXML
    private ToggleGroup apptRadio;

    @FXML
    private TableColumn<appointments, Integer> ATableContactID;

    @FXML
    private TableColumn<appointments, LocalDateTime> ATableCreateDate;

    @FXML
    private TableColumn<appointments, String> ATableCreatedBy;

    @FXML
    private TableColumn<appointments, Integer> ATableCustomerID;

    @FXML
    private TableColumn<appointments, String> ATableDescription;

    @FXML
    private TableColumn<appointments, LocalDateTime> ATableEnd;

    @FXML
    private TableColumn<appointments, Integer> ATableID;

    @FXML
    private TableColumn<appointments, LocalDateTime> ATableLastUpdate;

    @FXML
    private TableColumn<appointments, String> ATableLastUpdatedBy;

    @FXML
    private TableColumn<appointments, String> ATableLocation;

    @FXML
    private TableColumn<appointments, LocalDateTime> ATableStart;

    @FXML
    private TableColumn<appointments, String> ATableTitle;

    @FXML
    private TableColumn<appointments, String> ATableType;

    @FXML
    private TableColumn<appointments, Integer> ATableUserID;

    @FXML
    private TableColumn<customers, String> CTableAddress;

    @FXML
    private TableColumn<customers, LocalDateTime> CTableCreateDate;

    @FXML
    private TableColumn<customers, String> CTableCreatedBy;

    @FXML
    private TableColumn<customers, Integer> CTableID;

    @FXML
    private TableColumn<customers, LocalDateTime> CTableLastUpdate;

    @FXML
    private TableColumn<customers, String> CTableLastUpdatedBy;

    @FXML
    private TableColumn<customers, String> CTableName;

    @FXML
    private TableColumn<customers, String> CTablePostal;

    @FXML
    private Button addApptButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private TableView<appointments> apptTable;

    @FXML
    private TableView<customers> customerTable;

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

    private ObservableList<appointments> apptList = FXCollections.observableArrayList();
    private ObservableList<customers> customerList = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle){
        CreateApptTable();
        CreateCustomerTable();
    }

    private void CreateApptTable() {
        apptTable.setItems(apptList);
        apptList.setAll(tables.getAllAppointments());
        apptTable.refresh();
    }

    private void CreateCustomerTable() {
        //customerTable.setItems(customerList);
       // customerList.setAll(tables.getAllCustomers());
        //customerTable.refresh();
    }

    Stage stage;
    Parent scene;

    @FXML
    void apptAllRadio(ActionEvent event) {

    }
    @FXML
    void apptMonthRadio(ActionEvent event) {

    }

    @FXML
    void apptWeekRadio(ActionEvent event) {

    }

    @FXML
    void addApptButtonAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/addAppt.fxml")));
        stage.setScene(new Scene(scene,600,504));
        stage.show();
    }

    @FXML
    void addCustomerButtonAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/addCustomer.fxml")));
        stage.setScene(new Scene(scene,600,354));
        stage.show();
    }

    @FXML
    void deleteApptButtonAction(ActionEvent event) {

    }

    @FXML
    void deleteCustomerButtonAction(ActionEvent event) {

    }

    @FXML
    void editApptButtonAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/editAppt.fxml")));
        stage.setScene(new Scene(scene,600,504));
        stage.show();
    }

    @FXML
    void editCustomerButtonAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/editCustomer.fxml")));
        stage.setScene(new Scene(scene,600,354));
        stage.show();
    }

    @FXML
    void goToReportsButtonAction(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/javaclientsapp/reports.fxml")));
        stage.setScene(new Scene(scene,600,400));
        stage.show();
    }

}
