package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.time.*;
/**
 * class ZoneTimes.java
 */

/**
 * @author Justin Simons
 * */

/**
 *  ZoneTimes class handles all appointment data
 **/
public class ZoneTimes {

    private static ObservableList<LocalDateTime> localdateStartTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalDateTime> localdateEndTimes = FXCollections.observableArrayList();

    /**
     * getLocalDateStartTimes gets the localDateStartTimes
     * @return localdateStartTimes
     */
    public static ObservableList<LocalDateTime> getLocalDateStartTimes(){
        return localdateStartTimes;
    }
    /**
     * getLocalDateEndTimes gets the localdateEndTimes
     * @return localdateEndTimes
     */
    public static ObservableList<LocalDateTime> getLocalDateEndTimes(){
        return localdateEndTimes;
    }

    /**
     * setLocalStartTimes sets the local start times
     * first starts with a local time in 8:00 am EST.
     * it then goes through a for loop to change all times from 8:00 am to 9:45 pm EST to the localZoneID of those times.
     * finishes with a list of times from 8:00 am to 9:45 pm EST to the local time zone.
     * @return localStartTimes
     */
    public static ObservableList<LocalTime> setLocalStartTimes(){
        ObservableList<LocalTime> localStartTimes = FXCollections.observableArrayList();

        LocalTime localTime;
        LocalDate myLD = LocalDate.now();
        LocalDateTime ESTDateTime;
        ZoneId ESTZoneID = ZoneId.of("US/Eastern");
        ZoneId localZoneID = ZoneId.systemDefault();
        ZonedDateTime ESTZonedDateTime;

        LocalTime ESTTimes = LocalTime.of(8,0);
        long addMinutes = 15;
        for(int i = 0; i < 56 ; i++){
            ESTDateTime = LocalDateTime.of(myLD,ESTTimes);
            ESTZonedDateTime = ZonedDateTime.of(ESTDateTime,ESTZoneID);
            ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(ESTZonedDateTime.toInstant(), localZoneID);
            localdateStartTimes.add(localZonedDateTime.toLocalDateTime());
            localTime = localZonedDateTime.toLocalTime();
            localStartTimes.add(localTime);
            //add 15 minutes to time.
            ESTTimes = ESTTimes.plusMinutes(addMinutes);
        }
            return localStartTimes;
    }
    /**
     * setLocalEndTimes sets the local end times
     * first starts with a local time in 8:15 am EST.
     * it then goes through a for loop to change all times from 8:15 am to 10:00 pm EST to the localZoneID of those times.
     * finishes with a list of times from 8:15 am to 10:00 pm EST to the local time zone.
     * @return localEndfTimes
     */
    public static ObservableList<LocalTime> setLocalEndTimes() {
        ObservableList<LocalTime> localEndTimes = FXCollections.observableArrayList();
        LocalTime localTime;
        LocalDate myLD = LocalDate.now();
        LocalDateTime ESTDateTime;
        ZoneId ESTZoneID = ZoneId.of("US/Eastern");
        ZoneId localZoneID = ZoneId.systemDefault();
        ZonedDateTime ESTZonedDateTime;

        LocalTime ESTTimes = LocalTime.of(8,15);
        long addMinutes = 15;
        for(int i = 0; i < 56 ; i++){
            ESTDateTime = LocalDateTime.of(myLD,ESTTimes);
            ESTZonedDateTime = ZonedDateTime.of(ESTDateTime,ESTZoneID);
            ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(ESTZonedDateTime.toInstant(), localZoneID);
            localdateEndTimes.add(localZonedDateTime.toLocalDateTime());

            localTime = localZonedDateTime.toLocalTime();
            localEndTimes.add(localTime);

            //add 15 minutes to time.
            ESTTimes = ESTTimes.plusMinutes(addMinutes);
        }
        return localEndTimes;
    }
}