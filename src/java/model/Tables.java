package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tables {

    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    public static void addAppointment(Appointments newAppointment){allAppointments.add(newAppointment); }

    public static void addCustomer(Customers newCustomer){
        allCustomers.add(newCustomer);
    }

    public static ObservableList<Appointments> getAllAppointments(){
        return allAppointments;
    }

    public static ObservableList<Customers> getAllCustomers(){
        return allCustomers;
    }

}
