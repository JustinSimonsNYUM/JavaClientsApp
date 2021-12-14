package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import java.time.LocalDateTime;
/**
 * class Customers.java
 */

/**
 * @author Justin Simons
 * */

/**
 * Customers class handles all customer data
 * */

public class Customers {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private final ObjectProperty<LocalDateTime> createDate = new SimpleObjectProperty<>();
    private String createdBy;
    private final ObjectProperty<LocalDateTime> lastUpdate = new SimpleObjectProperty<>();
    private String lastUpdatedBy;
    private int divisionID;

    /**
     * Customers constructor sets all customer data
     * @param id holds customer ID
     * @param name holds customer name
     * @param address holds customer address
     * @param postalCode holds customer postalCode
     * @param phone holds customer phone
     * @param createDate holds customer createDate
     * @param createdBy holds customer createdBy
     * @param lastUpdate holds customer lastUpdate
     * @param lastUpdatedBy holds customer lastUpdatedBy
     * @param divisionID holds customer divisionID
     */
    public Customers(int id, String name, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate.set(createDate);
        this.createdBy = createdBy;
        this.lastUpdate.set(lastUpdate);
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }
    /**
     * getId gets the id
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * setId sets the id
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * getName gets the name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * setName sets the name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * getAddress gets the address
     * @return address
     */
    public String getAddress() {
        return address;
    }
    /**
     * setAddress sets the address
     * @param address String
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * getPostalCode gets the postalCode
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     * setPostalCode sets the postalCode
     * @param postalCode String
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /**
     * getPhone gets the phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * setPhone sets the phone
     * @param phone String
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * createDateProperty returns the ObjectProperty createDate
     * @return createDate
     */
    public ObservableValue<LocalDateTime> createDateProperty() { return createDate; }
    /**
     * getCreateDate returns createDate
     * @return createDate.get()
     */
    public LocalDateTime getCreateDate() {
        return createDate.get();
    }
    /**
     * setCreateDate sets the createDate value
     * @param createDate LocalDateTime createDate date and time
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate.set(createDate);
    }
    /**
     * lastUpdateProperty returns the ObjectProperty lastUpdate
     * @return lastUpdate
     */
    public ObservableValue<LocalDateTime> lastUpdateProperty() { return lastUpdate; }
    /**
     * getLastUpdate returns lastUpdate
     * @return lastUpdate.get()
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate.get();
    }
    /**
     * setLastUpdate sets the lastUpdate value
     * @param lastUpdate LocalDateTime lastUpdate date and time
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate.set(lastUpdate);
    }
    /**
     * getCreatedBy gets the createdBy
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * setCreatedBy sets the createdBy value
     * @param createdBy String
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * getLastUpdatedBy gets the lastUpdatedBy
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     * setLastUpdatedBy sets the lastUpdatedBy value
     * @param lastUpdatedBy String
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * getDivisionID gets the divisionID
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }
    /**
     * setDivisionID sets the divisionID
     * @param divisionID int
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}
