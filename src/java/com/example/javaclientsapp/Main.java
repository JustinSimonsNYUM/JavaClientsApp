package com.example.javaclientsapp;
import helper.DBQuery;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import model.Tables;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * class Main.java
 */

/**
 * @author Justin Simons
 * */

/**
 * main class starts the app
 * */

public class Main extends Application {
    /**
     * start starts the program
     * first calls fillTables()
     * creates a FXML loader to get the login.fxml
     * set's the controller to the loader
     * loads the loader to the root
     * creates a new stage
     *  set's the title
     * set's the scene
     * show's the stage
     * @param stage creates the primary stage
     * @throws SQLException gets thrown if mainScreen.fxml fails to load
     *      the catch sends alert stating "e.getMessage() + " closing program"
     */
    @Override
    public void start(Stage stage) throws SQLException {
        fillTables();
        try{
            ResourceBundle rb = ResourceBundle.getBundle("RB", Locale.getDefault());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 200);//login
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(rb.getString("stageTitle"));
            stage.show();
       }
       catch(Exception e){
           Alert a = new Alert(Alert.AlertType.ERROR);
           a.setContentText(e.getMessage() + " closing program");
           a.showAndWait();
           System.exit(0);
       }
    }

    /**
     * main launches the program
     * @param args set to launch
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }

    /**
     * fill tables first connects to the database
     * gets the result statement from all appointments
     * adds all appointment data to the class Tables allAppointments.
     * then gets all customers from the database and adds it to class Tables allCustomers.
     * @throws SQLException is called if it can't get a connection
     */
    void fillTables() throws SQLException {

        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        //add data to appointments table.
        try{
            String query = "SELECT * from appointments";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDate startDate = rs.getDate("Start").toLocalDate();
                LocalTime startTime = rs.getTime("Start").toLocalTime();
                LocalDateTime start = LocalDateTime.of(startDate,startTime).truncatedTo(ChronoUnit.MINUTES);
                LocalDate endDate = rs.getDate("End").toLocalDate();
                LocalTime endTime = rs.getTime("End").toLocalTime();
                LocalDateTime end = endDate.atTime(endTime).truncatedTo(ChronoUnit.MINUTES);
                LocalDate createDateDate = rs.getDate("Create_Date").toLocalDate();
                LocalTime createDateTime = rs.getTime("Create_Date").toLocalTime();
                LocalDateTime createDate = LocalDateTime.of(createDateDate,createDateTime).truncatedTo(ChronoUnit.MINUTES);
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime().truncatedTo(ChronoUnit.MINUTES);
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments appt = new Appointments(apptID,title,description,location,type,start,end,createDate,createdBy,lastUpdate,lastUpdatedBy,customerID,userID,contactID);
                Tables.addAppointment(appt);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        //add data to customers table
        try{
            String query = "SELECT * from customers";
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                int custID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDate createDateDate = rs.getDate("Create_Date").toLocalDate();
                LocalTime createDateTime = rs.getTime("Create_Date").toLocalTime();
                LocalDateTime createDate = createDateDate.atTime(createDateTime).truncatedTo(ChronoUnit.MINUTES);
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime().truncatedTo(ChronoUnit.MINUTES);
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divID = rs.getInt("Division_ID");

                Customers cust = new Customers(custID,name,address,postal,phone,createDate,createdBy,lastUpdate,lastUpdatedBy,divID);
                Tables.addCustomer(cust);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}