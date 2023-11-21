package com.example.locstreamserver.model;

import java.util.Objects;

public class Event {
//    public int setEventId;
    String timeStamp;
    int locatorId;
    int beaconId;
    int signalStrength;
    int eventId;

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

    public String getDBInsertString() {
        return  "(" + locatorId + "," + beaconId + "," + signalStrength + "," + "CONVERT(DATETIME, '" + timeStamp + "', 120)" + ")";

    }
}
