package com.example.locstreamserver.model;

import java.util.Objects;

public class mmWaveEvent {
    public  String startTime;
    public  String endTime;
    public  String macAddress;

    public String getDBInsertString() {
        // Check if any of the variables are null
        if (startTime == "" || endTime == "" || macAddress == "") {
            return "";
        }

        // All variables are non-null, construct the insert string
        return "(" + "'" + startTime + "'" + "," + "'" + endTime + "'" + "," + "'" + macAddress + "'"  + ")";
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "mmWaveEvent{" +
                "startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        mmWaveEvent that = (mmWaveEvent) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(macAddress, that.macAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, macAddress);
    }
}
