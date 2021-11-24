package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class tables {

    private static ObservableList<appointments> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<customers> allCustomers = FXCollections.observableArrayList();

    public static void addAppointment(appointments newAppointment){
        allAppointments.add(newAppointment);
    }

    public static void addCustomer(customers newCustomer){
        allCustomers.add(newCustomer);
    }

    public static ObservableList<appointments> getAllAppointments(){
        return allAppointments;
    }

    public static ObservableList<customers> getAllCustomers(){
        return allCustomers;
    }

}
