package model;



import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

import java.time.*;

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
    public LocalDateTime getStart() {
        ObjectProperty<LocalDateTime> localStart = new SimpleObjectProperty<>();
        LocalDate localDate = start.get().toLocalDate();
        LocalTime localTime = start.get().toLocalTime();
        LocalDateTime UTCDatetime = LocalDateTime.of(localDate, localTime);
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.of(UTCDatetime, ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = localZonedDateTime.toLocalDateTime();
        localStart.set(localDateTime);
        return localStart.get();
    }

    public void setStart(LocalDateTime start) { this.start.set(start); }

    public ObservableValue<LocalDateTime> endProperty() { return end; }

    public LocalDateTime getEnd() {
        ObjectProperty<LocalDateTime> localEnd = new SimpleObjectProperty<>();
        LocalDate localDate = end.get().toLocalDate();
        LocalTime localTime = end.get().toLocalTime();
        LocalDateTime UTCDatetime = LocalDateTime.of(localDate, localTime);
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.of(UTCDatetime, ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = localZonedDateTime.toLocalDateTime();
        localEnd.set(localDateTime);
        return localEnd.get();
    }

    public void setEnd(LocalDateTime end) { this.end.set(end); }

    public ObjectProperty<LocalDateTime> createDateProperty() { return createDate; }

    public LocalDateTime getCreateDate() {
        ObjectProperty<LocalDateTime> localCreateDate = new SimpleObjectProperty<>();
        LocalDate localDate = createDate.get().toLocalDate();
        LocalTime localTime = createDate.get().toLocalTime();
        LocalDateTime UTCDatetime = LocalDateTime.of(localDate, localTime);
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.of(UTCDatetime, ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = localZonedDateTime.toLocalDateTime();
        localCreateDate.set(localDateTime);
        return localCreateDate.get();
    }

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

    public LocalDateTime getLastUpdate() {
        ObjectProperty<LocalDateTime> localLastUpdate = new SimpleObjectProperty<>();
        LocalDate localDate = lastUpdate.get().toLocalDate();
        LocalTime localTime = lastUpdate.get().toLocalTime();
        LocalDateTime UTCDatetime = LocalDateTime.of(localDate, localTime);
        ZonedDateTime UTCZonedDateTime = ZonedDateTime.of(UTCDatetime, ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), ZoneId.systemDefault());
        LocalDateTime localDateTime = localZonedDateTime.toLocalDateTime();
        localLastUpdate.set(localDateTime);
        return localLastUpdate.get();
    }

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

}
