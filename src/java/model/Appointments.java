package model;



import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Appointments {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private final ObjectProperty<LocalDateTime> start = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> end = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> createDate = new SimpleObjectProperty<>();
    private String createdBy;
    private final ObjectProperty<LocalDateTime> lastUpdate = new SimpleObjectProperty<>();
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    public Appointments(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start.set(start);
        this.end.set(end);
        this.createDate.set(createDate);
        this.createdBy = createdBy;
        this.lastUpdate.set(lastUpdate);
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    public ObservableValue<LocalDateTime> startProperty() {
        return start;
    }
//**************************MAKE SUER THAT EVERY CHANGE IS SENDING THE INFOMATION IN UTC TIME.******************
    public LocalDateTime getStart() { return start.get(); }

    public void setStart(LocalDateTime start) { this.start.set(start); }

    public ObservableValue<LocalDateTime> endProperty() { return end; }

    public LocalDateTime getEnd() { return end.get(); }

    public void setEnd(LocalDateTime end) { this.end.set(end); }

    public ObjectProperty<LocalDateTime> createDateProperty() { return createDate; }

    public LocalDateTime getCreateDate() { return createDate.get(); }

    public void setCreateDate(LocalDateTime createDate) { this.createDate.set(createDate); }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ObservableValue<LocalDateTime> lastUpdateProperty() { return lastUpdate; }

    public LocalDateTime getLastUpdate() { return lastUpdate.get(); }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public static void checkUpcomingAppt(){
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        System.out.println(today);
        //LocalDateTime today = LocalDateTime.of(LocalDate.of(2021,12,9), LocalTime.of(15,0));
        boolean apptFound = false;
        String alertString = "";
        for(Appointments appt : Tables.getAllAppointments()){
            if(appt.getStart().isEqual(today) || appt.getStart().isEqual(today.plusMinutes(15)) || (appt.getStart().isAfter(today) && appt.getStart().isBefore(today.plusMinutes(15)))){
                alertString = "There is an upcoming appointment within 15 minutes:\n" +
                        "Appointment ID: " + appt.getId() +
                        "\nDate: " + appt.getStart().toLocalDate() +
                        "\nStart Time: " + appt.getStart().toLocalTime();
                apptFound = true;
                break;
            }
        }
        if(apptFound)
            myAlert(Alert.AlertType.INFORMATION, alertString);
        else
            myAlert(Alert.AlertType.CONFIRMATION, "There are no upcoming appointments within the next 15 minutes.");
    }

    public static void myAlert(Alert.AlertType alertType, String alert){
        Alert a = new Alert(alertType);
        a.setContentText(alert);
        a.showAndWait();
    }

}
