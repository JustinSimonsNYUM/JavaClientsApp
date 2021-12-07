package model;

import helper.DBQuery;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class Tables {

    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private static Appointments modifyAppt;
    private static Customers modifyCustomer;

    public static void addAppointment(Appointments newAppointment){ allAppointments.add(newAppointment); }

    //********************ADD THE NEW APPT TO THE ALLAPPOINTMENTS AND TO THE MySQL DATABASE
    public static void addNewAppointment(Appointments newAppointment) throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setStatement(connect);
        Statement statement = DBQuery.getStatement();
        //add data to appointments table.
        int id = newAppointment.getId();
        String title = newAppointment.getTitle();
        String description = newAppointment.getDescription();
        String location = newAppointment.getLocation();
        String type = newAppointment.getType();
        LocalDateTime start = newAppointment.getStart();
        LocalDateTime end = newAppointment.getEnd();
        LocalDateTime createDate = newAppointment.getCreateDate();
        String createdBy = newAppointment.getCreatedBy();
        LocalDateTime lastUpdate = newAppointment.getLastUpdate();
        String lastUpdatedBy = newAppointment.getLastUpdatedBy();
        int customerID = newAppointment.getCustomerID();
        int userID = newAppointment.getUserID();
        int contactID = newAppointment.getContactID();

        try {
            String query =
                    "INSERT INTO appointments\n" +
                    "VALUES (value1, value2, value3, ...);";
            statement.execute(query);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        allAppointments.add(newAppointment);
    }

    public static void addCustomer(Customers newCustomer){ allCustomers.add(newCustomer); }

    public static void setModifyAppt(Appointments appt){
        modifyAppt = new Appointments(appt.getId(),appt.getTitle(),appt.getDescription(),appt.getLocation(),appt.getType(),appt.getStart(),appt.getEnd(),appt.getCreateDate(),appt.getCreatedBy(),appt.getLastUpdate(),appt.getLastUpdatedBy(),appt.getCustomerID(),appt.getUserID(),appt.getContactID());
    }

    public static Appointments getModifyAppt(){ return modifyAppt; }

    public static void updateAppt(int index, Appointments selectedAppt){
        for(Appointments appt: allAppointments){
            if(appt.getId() == selectedAppt.getId())
                allAppointments.set(index, selectedAppt);
        }
    }

    public static void setModifyCustomer(Customers customer){
        modifyCustomer = new Customers(customer.getId(),customer.getName(),customer.getAddress(),customer.getPostalCode(),customer.getPhone(),customer.getCreateDate(),customer.getCreatedBy(),customer.getLastUpdate(),customer.getLastUpdatedBy(),customer.getDivisionID());
    }

    public static Customers getModifyCustomer(){ return modifyCustomer; }

    public static void updateCustomer(int index, Customers selectedCustomer){
        for(Customers customer: allCustomers){
            if(customer.getId() == selectedCustomer.getId())
                allCustomers.set(index, selectedCustomer);
        }
    }

    public static boolean deleteCustomer(Customers selectedCustomer){
        boolean found = false;

        if(allCustomers.contains(selectedCustomer)){
            int selectedCustomerID = selectedCustomer.getId();
            //first delete all customer associated appointments
            for(Customers customer: allCustomers){
                if(customer.getId() == selectedCustomerID){
                    allAppointments.removeIf(appt -> appt.getCustomerID() == selectedCustomerID);
                }
            }
            //delete customer
            allCustomers.remove(selectedCustomer);
            found = true;
        }
        return found;
    }

    public static boolean deleteAppointment(Appointments selectedAppt){
        boolean found = false;

        if(allAppointments.contains(selectedAppt)){
            allAppointments.remove(selectedAppt);
            found = true;
        }
        return found;
    }
//*******************CHANGE APPOINTMENT TIMES TO lOCAL TIMES****************
    public static ObservableList<Appointments> getAllAppointments(){
        /*
        ObjectProperty<LocalDateTime> localStart = new SimpleObjectProperty<>();
        LocalDate localDate = start.get().toLocalDate();
        LocalTime localTime = start.get().toLocalTime();
        LocalDateTime UTCDatetime = LocalDateTime.of(localDate, localTime);
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.of(UTCDatetime, ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = localZonedDateTime.toLocalDateTime();
        localStart.set(localDateTime);
         */
        return allAppointments;
    }

    public static ObservableList<Customers> getAllCustomers(){
        return allCustomers;
    }

}
