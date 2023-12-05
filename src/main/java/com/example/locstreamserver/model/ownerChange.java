package com.example.locstreamserver.model;

import java.util.Objects;

public class ownerChange {
    String timeStamp;
    int newOwner;
    int previousOwner;
    int beaconId;


    public String getTimestamp() {
        return timeStamp;
    }

    public void setTimestamp(String timestamp) {
        this.timeStamp = timestamp;
    }

    public int getNewOwner() {
        return newOwner;
    }

    public void setNewOwner(int newOwner) {
        this.newOwner = newOwner;
    }

    public int getPreviousOwner() {
        return previousOwner;
    }

    public void setPreviousOwner(int previousOwner) {
        this.previousOwner = previousOwner;
    }

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    @Override
    public String toString() {
        return "ownerChange{" +
                "timestamp='" + timeStamp + '\'' +
                ", newOwner=" + newOwner +
                ", previousOwner=" + previousOwner +
                ", beaconId=" + beaconId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ownerChange that = (ownerChange) o;
        return newOwner == that.newOwner && previousOwner == that.previousOwner && beaconId == that.beaconId && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, newOwner, previousOwner, beaconId);
    }
}
