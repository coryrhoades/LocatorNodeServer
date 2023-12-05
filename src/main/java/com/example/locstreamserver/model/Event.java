package com.example.locstreamserver.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Event {
//    public int setEventId;
    public String timeStamp;
    int locatorId;
    int beaconId;
    int signalStrength;
    String macAddress;
    int eventId;

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }





    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getLocatorId() {
        return locatorId;
    }

    public void setLocatorId(int locatorId) {
        this.locatorId = locatorId;
    }

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }

    public int getEventId() {
        return eventId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return locatorId == event.locatorId && beaconId == event.beaconId && signalStrength == event.signalStrength && eventId == event.eventId && Objects.equals(timeStamp, event.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, locatorId, beaconId, signalStrength, eventId);
    }

    @Override
    public String toString() {
        return "Event{" +
                "timeStamp='" + timeStamp + '\'' +
                ", locatorId=" + locatorId +
                ", beaconId=" + beaconId +
                ", signalStrength=" + signalStrength +
                ", eventId=" + eventId +
                '}';
    }

    //public String getDBInsertString() {
    //    return  "(" + locatorId + "," + beaconId + "," + signalStrength + "," + "CONVERT(DATETIME, '" + timeStamp + "', 120)" + ")";

//    }

    public String getDBInsertString() {
        // Check if any of the variables are null
        if (locatorId == 0 || beaconId == 0 || signalStrength == 0 || timeStamp == null || timeStamp == "") {
            return "";
        }

        // All variables are non-null, construct the insert string
        return "(" + locatorId + "," + beaconId + "," + signalStrength + "," + "'" + timeStamp + "'" + ")";
    }


    public long convertToEpoch() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(timeStamp, formatter);

        long unixTimestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        //System.out.println("Unix Timestamp: " + unixTimestamp);
        return unixTimestamp;
    }

}
