package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tables {

    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();
    private static Appointments modifyAppt;
    private static Customers modifyCustomer;

    public static void addAppointment(Appointments newAppointment){allAppointments.add(newAppointment); }

    public static void addCustomer(Customers newCustomer){ allCustomers.add(newCustomer); }

    public static void setModifyAppt(Appointments appt){
        modifyAppt = new Appointments(appt.getId(),appt.getTitle(),appt.getDescription(),appt.getLocation(),appt.getType(),appt.getStart(),appt.getEnd(),appt.getCreateDate(),appt.getCreatedBy(),appt.getLastUpdate(),appt.getLastUpdatedBy(),appt.getCustomerID(),appt.getUserID(),appt.getContactID());
    }

    public static Appointments getModifyAppt(){ return modifyAppt; }

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

    public static ObservableList<Appointments> getAllAppointments(){ return allAppointments; }

    public static ObservableList<Customers> getAllCustomers(){
        return allCustomers;
    }

}
