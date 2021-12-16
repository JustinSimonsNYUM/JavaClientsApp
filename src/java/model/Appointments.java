package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import java.time.*;
import java.time.temporal.ChronoUnit;
/**
 * class Appointments.java
 */

/**
 * @author Justin Simons
 * */

/**
 * Appointments class handles all appointment data
 **/
public class Appointments {

    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    /**
     * start holds the localDateTime start date and time of the appointment
     */
    private final ObjectProperty<LocalDateTime> start = new SimpleObjectProperty<>();
    /**
     * end holds the localDateTime end date and time of the appointment
     */
    private final ObjectProperty<LocalDateTime> end = new SimpleObjectProperty<>();
    /**
     * createDate holds the localDateTime createDate date and time of the appointment
     */
    private final ObjectProperty<LocalDateTime> createDate = new SimpleObjectProperty<>();
    private String createdBy;
    /**
     * lastUpdate holds the localDateTime lastUpdate date and time of the appointment
     */
    private final ObjectProperty<LocalDateTime> lastUpdate = new SimpleObjectProperty<>();
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Appointments constructor sets all appointment data
     * @param id holds appt ID
     * @param title holds appt title
     * @param description holds appt description
     * @param location holds appt location
     * @param type holds appt type
     * @param start holds appt start
     * @param end holds appt end
     * @param createDate holds appt createDate
     * @param createdBy holds appt createdBy
     * @param lastUpdate holds appt lastUpdate
     * @param lastUpdatedBy holds appt lastUpdatedBy
     * @param customerID holds appt cusomerID
     * @param userID holds appt userID
     * @param contactID holds appt contactID
     */
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

    /**
     * startProperty returns the ObjectProperty start
     * @return start
     */
    public ObservableValue<LocalDateTime> startProperty() {
        return start;
    }
    /**
     * getStart returns start
     * @return start.get()
     */
    public LocalDateTime getStart() { return start.get(); }
    /**
     * setStart sets the start value
     * @param start LocalDateTime start date and time
     */
    public void setStart(LocalDateTime start) { this.start.set(start); }
    /**
     * endProperty returns the ObjectProperty end
     * @return end
     */
    public ObservableValue<LocalDateTime> endProperty() { return end; }
    /**
     * getEnd returns end
     * @return end.get()
     */
    public LocalDateTime getEnd() { return end.get(); }
    /**
     * setEnd sets the end value
     * @param end LocalDateTime end date and time
     */
    public void setEnd(LocalDateTime end) { this.end.set(end); }
    /**
     * createDateProperty returns the ObjectProperty createDate
     * @return createDate
     */
    public ObjectProperty<LocalDateTime> createDateProperty() { return createDate; }
    /**
     * getCreateDate returns
     * @return createDate.get()
     */
    public LocalDateTime getCreateDate() { return createDate.get(); }
    /**
     * setCreateDate sets the start value
     * @param createDate LocalDateTime createDate date and time
     */
    public void setCreateDate(LocalDateTime createDate) { this.createDate.set(createDate); }
    /**
     * getId gets the id
     * @return id
     */
    public int getId() { return id; }

    /**
     * setId sets the id
     * @param id int
     */
    public void setId(int id) { this.id = id; }
    /**
     * getTitle gets the title
     * @return title
     */
    public String getTitle() {
        return title;
    }
    /**
     * setTitle sets the title
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * getDescription gets the description
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * setDescription sets the description
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * getLocation gets the location
     * @return location
     */
    public String getLocation() {
        return location;
    }
    /**
     * setLocation sets the location
     * @param location String
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * getType gets the type
     * @return type
     */
    public String getType() {
        return type;
    }
    /**
     * setType sets the type
     * @param type String
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * getCreatedBy gets the createdBy
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * setCreatedBy sets the createdBy
     * @param createdBy String
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * lastUpdateProperty returns the ObservableValue lastUpdate
     * @return lastUpdate
     */
    public ObservableValue<LocalDateTime> lastUpdateProperty() { return lastUpdate; }
    /**
     * getLastUpdate returns
     * @return lastUpdate.get()
     */
    public LocalDateTime getLastUpdate() { return lastUpdate.get(); }
    /**
     * setLastUpdate sets the lastUpdate value
     * @param lastUpdate LocalDateTime lastUpdate date and time
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }
    /**
     * getLastUpdatedBy gets the lastUpdatedBy
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     * setLastUpdatedBy sets the lastUpdatedBy
     * @param lastUpdatedBy String
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * getCustomerID gets the customerID
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }
    /**
     * setCustomerID sets the customerID
     * @param customerID int
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * getUserID gets the userID
     * @return userID
     */
    public int getUserID() {
        return userID;
    }
    /**
     * setUserID sets the userID
     * @param userID int
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /**
     * getContactID gets the contactID
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * setContactID sets the contactID
     * @param contactID int
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * checkUpcomingAppt looks for upcoming appointments within 15 minutes of the user logging in.
     * first it gets the LocalDateTime.now()
     * it looks through all appointments start date/time to see if any of them start within the next 15 minutes.
     * If there are any appointments that do, they will appear in a information alert with the appt id and date/time.
     * if there are no appointments within 15 minutes of logging in, an information alert will show saying that no appointments will appear in 15 minutes.
     */
    public static void checkUpcomingAppt(){
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
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
    /**
     * myAlert shows an alert.
     * @param alertType gets the alert type
     * @param alert gets the string that will be presented in the alert
     */
    public static void myAlert(Alert.AlertType alertType, String alert){
        Alert a = new Alert(alertType);
        a.setContentText(alert);
        a.showAndWait();
    }

}
