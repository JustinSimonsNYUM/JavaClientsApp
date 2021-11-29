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
import model.Appointments;
import model.Customers;
import model.Tables;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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
    }

    private void CreateApptTable() {
        apptTable.setItems(apptList);
        apptList.setAll(Tables.getAllAppointments());
        apptTable.refresh();
    }

    private void CreateCustomerTable() {
        //customerTable.setItems(customerList);
       // customerList.setAll(Tables.getAllCustomers());
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
    @FXML
    void closeAppButton(ActionEvent event) {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
