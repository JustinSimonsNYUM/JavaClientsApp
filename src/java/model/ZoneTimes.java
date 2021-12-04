package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
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
/*
        System.out.println(ZoneId.systemDefault());
        ZoneId.getAvailableZoneIds().stream().sorted().forEach(System.out::println);
        ZoneId.getAvailableZoneIds().stream().filter(z->z.contains("America")).sorted().forEach(System.out::println);


        LocalTime myLT = LocalTime.of(22,0);
        LocalDate myLD = LocalDate.now();
        LocalDateTime myLDT = LocalDateTime.of(myLD,myLT);
        ZoneId myZoneID = ZoneId.of("America/New_York");
        ZonedDateTime myZDT = ZonedDateTime.of(myLDT,myZoneID);


        ZoneId utcZID = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), utcZID);
        System.out.println("User time to UTC:" + utcZDT);

        ZonedDateTime myNewZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), myZoneID);
        System.out.println("UTC to User Time: " + myNewZDT);

        System.out.println(myZDT);
        System.out.println(myZDT.toLocalDate());
        System.out.println(myZDT.toLocalDateTime());
        System.out.println(myZDT.toOffsetDateTime());
        System.out.println(myZDT.toInstant());
        System.out.println(myZDT.toLocalDate().toString() + " " + myZDT.toLocalTime().toString());


*/