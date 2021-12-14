package model;

import helper.DBQuery;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;
/**
 * class Tables.java
 */

/**
 * @author Justin Simons
 * */

/**
 * Tables class handles all the table appointment and customer data
 **/
public class Tables {

    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private static Appointments modifyAppt;
    private static Customers modifyCustomer;

    /**
     * addAppointment gets an appointment and adds it to the tables list of all appointments.
     * @param newAppointment Appointments
     */
    public static void addAppointment(Appointments newAppointment){ allAppointments.add(newAppointment); }

    /**
     * addNewAppointment receives a new appointment from the add appointment page and adds it to the tables
     * all appointments and to the database.
     * first gets a connection to the database.
     * creates a sql statement to add the new appointment to the database.
     * adds each piece of data from the newAppointment to the prepared statement.
     * then adds the appointment to tables list of all appointments.
     * @param newAppointment receives an Appointment
     * @throws SQLException thrown if no connection to the database is made.
     */
    public static void addNewAppointment(Appointments newAppointment) throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setPreparedStatement(connect,"INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        //add data to appointments table.
       try {
           preparedStatement.setInt(1,newAppointment.getId());
           preparedStatement.setString(2,newAppointment.getTitle());
           preparedStatement.setString (3, newAppointment.getDescription());
           preparedStatement.setString (4,newAppointment.getLocation());
           preparedStatement.setString (5,newAppointment.getType());
           preparedStatement.setObject (6,newAppointment.getStart());
           preparedStatement.setObject (7,newAppointment.getEnd());
           preparedStatement.setObject (8,newAppointment.getCreateDate());
           preparedStatement.setString (9,newAppointment.getCreatedBy());
           preparedStatement.setTimestamp (10,Timestamp.valueOf(newAppointment.getLastUpdate()));
           preparedStatement.setString (11,newAppointment.getLastUpdatedBy());
           preparedStatement.setInt (12,newAppointment.getCustomerID());
           preparedStatement.setInt (13,newAppointment.getUserID());
           preparedStatement.setInt (14,newAppointment.getContactID());

           preparedStatement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        allAppointments.add(newAppointment);
    }
    /**
     * addCustomer gets a customer and adds it to the tables list of all customers.
     * @param customers Customers
     */
    public static void addCustomer(Customers customers){ allCustomers.add(customers); }
    /**
     * addNewCustomer receives a new customer from the add customer page and adds it to the tables
     * all customers and to the database.
     * first gets a connection to the database.
     * creates a sql statement to add the new customer to the database.
     * adds each piece of data from the newCustomer to the prepared statement.
     * then adds the customer to tables list of all customer.
     * @param newCustomer receives a Customers
     * @throws SQLException thrown if no connection to the database is made.
     */    public static void addNewCustomer(Customers newCustomer) throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setPreparedStatement(connect,"INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        //add data to appointments table.
        try {
            preparedStatement.setInt(1,newCustomer.getId());
            preparedStatement.setString(2,newCustomer.getName());
            preparedStatement.setString (3, newCustomer.getAddress());
            preparedStatement.setString (4,newCustomer.getPostalCode());
            preparedStatement.setString (5,newCustomer.getPhone());
            preparedStatement.setObject (6,newCustomer.getCreateDate());
            preparedStatement.setString (7,newCustomer.getCreatedBy());
            preparedStatement.setTimestamp (8,Timestamp.valueOf(newCustomer.getLastUpdate()));
            preparedStatement.setString (9,newCustomer.getLastUpdatedBy());
            preparedStatement.setInt (10, newCustomer.getDivisionID());

            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        allCustomers.add(newCustomer);
    }

    /**
     * setModifyAppt receives the appointment to modify in the edit appointment page
     * @param appt Appointments
     */
    public static void setModifyAppt(Appointments appt){
        modifyAppt = new Appointments(appt.getId(),appt.getTitle(),appt.getDescription(),appt.getLocation(),appt.getType(),appt.getStart(),appt.getEnd(),appt.getCreateDate(),appt.getCreatedBy(),appt.getLastUpdate(),appt.getLastUpdatedBy(),appt.getCustomerID(),appt.getUserID(),appt.getContactID());
    }

    /**
     * getModifyAppt returns the appointment to be modified
     * @return Appointments
     */
    public static Appointments getModifyAppt(){ return modifyAppt; }

    /**
     * updateAppt takes the updated appointment and replaces it with the old version of the appointment
     * first is gets a connection with the database.
     * then it sets each part of the old version of the appointment with the new data in the database
     * then it replaces the old version of the appointment with the new version in the list of all appointments
     * @param index int. index of the edited appointment
     * @param selectedAppt Appointments. the modified appointment
     * @throws SQLException thrown if no connection to the database is made.
     */
    public static void updateAppt(int index, Appointments selectedAppt) throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setPreparedStatement(connect,"UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ? ;");
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        //add data to appointments table.
        try {
            preparedStatement.setString(1,selectedAppt.getTitle());
            preparedStatement.setString (2, selectedAppt.getDescription());
            preparedStatement.setString (3,selectedAppt.getLocation());
            preparedStatement.setString (4,selectedAppt.getType());
            preparedStatement.setObject (5,selectedAppt.getStart());
            preparedStatement.setObject (6,selectedAppt.getEnd());
            preparedStatement.setObject (7,selectedAppt.getCreateDate());
            preparedStatement.setString (8,selectedAppt.getCreatedBy());
            preparedStatement.setTimestamp (9,Timestamp.valueOf(selectedAppt.getLastUpdate()));
            preparedStatement.setString (10,selectedAppt.getLastUpdatedBy());
            preparedStatement.setInt (11,selectedAppt.getCustomerID());
            preparedStatement.setInt (12,selectedAppt.getUserID());
            preparedStatement.setInt (13,selectedAppt.getContactID());
            preparedStatement.setInt(14,selectedAppt.getId());

            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


        for(Appointments appt: allAppointments){
            if(appt.getId() == selectedAppt.getId())
                allAppointments.set(index, selectedAppt);
        }
    }
    /**
     * setModifyCustomer receives the customer to modify in the edit customer page
     * @param customer Customers
     */
    public static void setModifyCustomer(Customers customer){
        modifyCustomer = new Customers(customer.getId(),customer.getName(),customer.getAddress(),customer.getPostalCode(),customer.getPhone(),customer.getCreateDate(),customer.getCreatedBy(),customer.getLastUpdate(),customer.getLastUpdatedBy(),customer.getDivisionID());
    }
    /**
     * getModifyCustomer returns the customer to be modified
     * @return Customers
     */
    public static Customers getModifyCustomer(){ return modifyCustomer; }
    /**
     * updateCustomer takes the updated customer and replaces it with the old version of the customer
     * first is gets a connection with the database.
     * then it sets each part of the old version of the customer with the new data in the database
     * then it replaces the old version of the customer with the new version in the list of all customers
     * @param index int. index of the edited customer
     * @param selectedCustomer Customers. the modified customer
     * @throws SQLException thrown if no connection to the database is made.
     */
    public static void updateCustomer(int index, Customers selectedCustomer) throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setPreparedStatement(connect,"UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?;");
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        //add data to appointments table.
        try {
            preparedStatement.setString(1,selectedCustomer.getName());
            preparedStatement.setString (2, selectedCustomer.getAddress());
            preparedStatement.setString (3,selectedCustomer.getPostalCode());
            preparedStatement.setString (4,selectedCustomer.getPhone());
            preparedStatement.setObject (5,selectedCustomer.getCreateDate());
            preparedStatement.setString (6,selectedCustomer.getCreatedBy());
            preparedStatement.setTimestamp (7,Timestamp.valueOf(selectedCustomer.getLastUpdate()));
            preparedStatement.setString (8,selectedCustomer.getLastUpdatedBy());
            preparedStatement.setInt (9, selectedCustomer.getDivisionID());
            preparedStatement.setInt(10,selectedCustomer.getId());

            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        for(Customers customer: allCustomers){
            if(customer.getId() == selectedCustomer.getId())
                allCustomers.set(index, selectedCustomer);
        }
    }

    /**
     * deleteCustomer deletes the customer and their associated appointments.
     * first connects to the database.
     * next goes through all customers to make sure the correct one is selected.
     * next it executes the query DELETE FROM appointments WHERE Customer_ID = ?; and sets the ? to the customer ID
     * next it executes the query DELETE FROM customers WHERE Customer_ID = ?; and sets the ? to the customer ID
     * next it removes the deleted appointments from the all appointments using a lambda expression
     * next it removes the deleted customer from the all customers using a lambda expression
     * the two lambda expressions simplifies removing every appointment and customer from the Observable lists
     * @param selectedCustomer Customers. the customer to delete
     * @return customerFound. true if a customer was found and deleted
     * @throws SQLException thrown if connection to database is not made
     */
    public static boolean deleteCustomer(Customers selectedCustomer) throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setPreparedStatement(connect,"DELETE FROM appointments WHERE Customer_ID = ?;");
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        boolean customerFound = false;
        for(Customers customer: allCustomers){
            if (customer.getId() == selectedCustomer.getId()) {
                customerFound = true;
                break;
            }
        }
        if(customerFound){
            //first delete customer appts in DB
            try {
                preparedStatement.setInt(1,selectedCustomer.getId());
                preparedStatement.executeUpdate();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            //next delete the customer from the DB
            DBQuery.setPreparedStatement(connect,"DELETE FROM customers WHERE Customer_ID = ?;");
            PreparedStatement preparedStatement2 = DBQuery.getPreparedStatement();
            try {
                preparedStatement2.setInt(1,selectedCustomer.getId());
                preparedStatement2.executeUpdate();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            allAppointments.removeIf(appt -> appt.getCustomerID() == selectedCustomer.getId());

            // next delete customer
            allCustomers.removeIf(customer -> customer.getId() == selectedCustomer.getId());
        }
        return customerFound;
    }
    /**
     * deleteAppointment deletes the appointment all appointments and the database.
     * first connects to the database.
     * next goes through all appointments to make sure the correct one is selected.
     * next it executes the query DELETE FROM appointments WHERE Appointment_ID = ?; and sets the ? to the customer ID
     * next it removes the deleted appointments from the all appointments using a lambda expression
     * the lambda expression simplifies removing every appointment from the Observable list
     * @param selectedAppt Appointments. the appointments to delete
     * @return found. true if an appointment was found and deleted
     * @throws SQLException thrown if connection to database is not made
     **/
    public static boolean deleteAppointment(Appointments selectedAppt) throws SQLException {
        Connection connect = JDBC.getConnection();
        DBQuery.setPreparedStatement(connect,"DELETE FROM appointments WHERE Appointment_ID = ?;");
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
        //add data to appointments table.
        boolean found = false;
        for(Appointments appt: allAppointments){
            if (appt.getId() == selectedAppt.getId()) {
                found = true;
                break;
            }
        }
        if(found){
            try {
                preparedStatement.setInt(1,selectedAppt.getId());
                preparedStatement.executeUpdate();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }

            allAppointments.removeIf(appt -> appt.getId() == selectedAppt.getId());
        }
        return found;
    }

    /**
     * getAllAppointments returns all appointments in LocalDate and LocalTime
     * if goes through all the data from each appointment and changes the data/times from UTC to  Local.
     * @return localAppts
     */
    public static ObservableList<Appointments> getAllAppointments(){
        ObservableList<Appointments> localAppts = FXCollections.observableArrayList();
        for(Appointments appt: allAppointments) {
            int id = appt.getId();
            String title = appt.getTitle();
            String description = appt.getDescription();
            String location = appt.getLocation();
            String type = appt.getType();
            //change start to local time
            LocalDateTime UTCStart = appt.getStart();
            ZonedDateTime UTCZonedDateTime = ZonedDateTime.of(UTCStart, ZoneId.of("UTC"));
            ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
            LocalDateTime localStart = localZonedDateTime.toLocalDateTime();
            //change end to local time
            LocalDateTime UTCEnd = appt.getEnd();
            UTCZonedDateTime = ZonedDateTime.of(UTCEnd, ZoneId.of("UTC"));
            localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
            LocalDateTime localEnd = localZonedDateTime.toLocalDateTime();
            //change createDate to local time
            LocalDateTime UTCcreateDate = appt.getCreateDate();
            UTCZonedDateTime = ZonedDateTime.of(UTCcreateDate, ZoneId.of("UTC"));
            localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
            LocalDateTime localCreateDate = localZonedDateTime.toLocalDateTime();

            String createdBy = appt.getCreatedBy();
            //change lastUpdate to local time
            LocalDateTime UTClastUpdate = appt.getLastUpdate();
            UTCZonedDateTime = ZonedDateTime.of(UTClastUpdate, ZoneId.of("UTC"));
            localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
            LocalDateTime localLastUpdate = localZonedDateTime.toLocalDateTime();

            String lastUpdatedBy = appt.getLastUpdatedBy();
            int customerID = appt.getCustomerID();
            int userID = appt.getUserID();
            int contactID = appt.getContactID();

            localAppts.add(new Appointments(id, title, description, location, type, localStart, localEnd, localCreateDate, createdBy, localLastUpdate, lastUpdatedBy, customerID, userID, contactID));
        }
        return localAppts;
    }
    /**
     * getAllCustomers returns all customers in LocalDate and LocalTime
     * if it goes through all the data from each customer and changes the data/times from UTC to  Local.
     * @return localCustomer
     */
    public static ObservableList<Customers> getAllCustomers(){
        ObservableList<Customers> localCustomers = FXCollections.observableArrayList();
        for(Customers customer : allCustomers){
            int id = customer.getId();
            String name = customer.getName();
            String address = customer.getAddress();
            String postalCode = customer.getPostalCode();
            String phone = customer.getPhone();
            //change create date from UTC to local
            LocalDateTime UTCCreateDate = customer.getCreateDate();
            ZonedDateTime UTCZonedDateTime = ZonedDateTime.of(UTCCreateDate, ZoneId.of("UTC"));
            ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
            LocalDateTime localCreateDate = localZonedDateTime.toLocalDateTime();
            String createdBy = customer.getCreatedBy();
            //change last update from UTC to local
            LocalDateTime UTCLastUpdate = customer.getLastUpdate();
            UTCZonedDateTime = ZonedDateTime.of(UTCLastUpdate, ZoneId.of("UTC"));
            localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
            LocalDateTime localLastUpdate = localZonedDateTime.toLocalDateTime();
            String lastUpdatedBy = customer.getLastUpdatedBy();
            int divisionID = customer.getDivisionID();
            localCustomers.add(new Customers(id,name,address,postalCode,phone,localCreateDate,createdBy,localLastUpdate,lastUpdatedBy,divisionID));
            }

        return localCustomers;
    }

}
