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


public class Main extends Application {

    @Override
    public void start(Stage stage) throws SQLException {
        fillTables();
        //Appointments apptDemo = new Appointments(1,"hello","hello","location","type",null,null,null,"createdBy",null,"lastUpdatedBy",1,2,3);
        //Tables.addAppointment(apptDemo);

        try{
           FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("logIn.fxml"));
           Scene scene = new Scene(fxmlLoader.load(), 400, 200);
           stage.setScene(scene);
           stage.setResizable(false);
           stage.setTitle("Java Client App");
           stage.show();
       }
       catch(Exception e){
           Alert a = new Alert(Alert.AlertType.ERROR);
           a.setContentText(e.getMessage() + " closing program");
           a.showAndWait();
           System.exit(0);
       }
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }

    void fillTables() throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        System.out.println("hello");
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
                LocalDateTime start = startDate.atTime(startTime);
                LocalDate endDate = rs.getDate("End").toLocalDate();
                LocalTime endTime = rs.getTime("End").toLocalTime();
                LocalDateTime end = endDate.atTime(endTime);
                LocalDate createDateDate = rs.getDate("Create_Date").toLocalDate();
                LocalTime createDateTime = rs.getTime("Create_Date").toLocalTime();
                LocalDateTime createDate = createDateDate.atTime(createDateTime);
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
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
                LocalDateTime createDate = createDateDate.atTime(createDateTime);
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divID = rs.getInt("Division_ID");

                Customers cust = new Customers(custID,name,address,postal,phone,createDate,createdBy,lastUpdate,lastUpdatedBy,divID);
                Tables.addCustomer(cust);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /*Connection connect = JDBC.getConnection();
        String insertStatement = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Updated_By) VALUES (?,?,?,?)";

        DBQuery.setPreparedStatement(connect, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        String countryName;
        String date = "2021-11-12 00:00:00";
        String createdBy = "admin";
        String lastUpdatedBy = "admin";

        Scanner keyboard = new Scanner(System.in);
        countryName = keyboard.nextLine();*/
    /*how to process a result set to use data from a DB
    Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        try{
            String s = "SELECT * from countries";
            statement.execute(s);
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                int Country_ID = rs.getInt("Country_ID");
                String Country = rs.getString("Country");
                LocalDate date = rs.getDate("Create_Date").toLocalDate();
                LocalTime time = rs.getTime("Create_Date").toLocalTime();
                String Created_By = rs.getString("Created_By");
                LocalDateTime Last_Update = rs.getTimestamp("Last_Update").toLocalDateTime();

                System.out.println(Country_ID + " | " + Country + " | " + date + " | " + time + " | " + Created_By + " | " + Last_Update);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
*/
    /* how to use SQL query on DB
    Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        String insertSQL = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Updated_By)" +
                "VALUES ('US', '2021-11-12', 'admin', 'admin')";

        statement.execute(insertSQL);

        //rows affected
        if(statement.getUpdateCount() > 0)
            System.out.println(statement.getUpdateCount() + " rows affected in statement.");
        else
            System.out.println("nothing was added");
        */
}