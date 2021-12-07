package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.time.*;

public class ZoneTimes {

    private static ObservableList<LocalDateTime> localdateStartTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalDateTime> localdateEndTimes = FXCollections.observableArrayList();

    public static ObservableList<LocalDateTime> getLocalDateStartTimes(){
        return localdateStartTimes;
    }

    public static ObservableList<LocalDateTime> getLocalDateEndTimes(){
        return localdateEndTimes;
    }

    public static ObservableList<LocalTime> setLocalStartTimes(){
        ObservableList<LocalTime> localStartTimes = FXCollections.observableArrayList();

        LocalTime localTime;
        LocalDate myLD = LocalDate.now();
        LocalDateTime ESTDateTime;
        ZoneId ESTZoneID = ZoneId.of("US/Eastern");
        ZoneId UTCZoneID = ZoneId.of("UTC");
        ZoneId localZoneID = ZoneId.systemDefault();
        ZonedDateTime ESTZonedDateTime;

        LocalTime ESTTimes = LocalTime.of(8,0);
        long addMinutes = 15;
        for(int i = 0; i < 56 ; i++){
            ESTDateTime = LocalDateTime.of(myLD,ESTTimes);
            ESTZonedDateTime = ZonedDateTime.of(ESTDateTime,ESTZoneID);
            ZonedDateTime UTCZonedDateTime = ZonedDateTime.ofInstant(ESTZonedDateTime.toInstant(), UTCZoneID);
            ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), localZoneID);
            localdateStartTimes.add(localZonedDateTime.toLocalDateTime());
            localTime = localZonedDateTime.toLocalTime();
            localStartTimes.add(localTime);
            //add 15 minutes to time.
            ESTTimes = ESTTimes.plusMinutes(addMinutes);
        }
            return localStartTimes;
    }

    public static ObservableList<LocalTime> setLocalEndTimes() {
        ObservableList<LocalTime> localEndTimes = FXCollections.observableArrayList();
        LocalTime localTime;
        LocalDate myLD = LocalDate.now();
        LocalDateTime ESTDateTime;
        ZoneId ESTZoneID = ZoneId.of("US/Eastern");
        ZoneId UTCZoneID = ZoneId.of("UTC");
        ZoneId localZoneID = ZoneId.systemDefault();
        ZonedDateTime ESTZonedDateTime;

        LocalTime ESTTimes = LocalTime.of(8,15);
        long addMinutes = 15;
        for(int i = 0; i < 56 ; i++){
            ESTDateTime = LocalDateTime.of(myLD,ESTTimes);
            ESTZonedDateTime = ZonedDateTime.of(ESTDateTime,ESTZoneID);
            ZonedDateTime UTCZonedDateTime = ZonedDateTime.ofInstant(ESTZonedDateTime.toInstant(), UTCZoneID);
            ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(UTCZonedDateTime.toInstant(), localZoneID);
            localdateEndTimes.add(localZonedDateTime.toLocalDateTime());

            localTime = localZonedDateTime.toLocalTime();
            localEndTimes.add(localTime);
            //add 15 minutes to time.
            ESTTimes = ESTTimes.plusMinutes(addMinutes);
        }
        return localEndTimes;
    }
}