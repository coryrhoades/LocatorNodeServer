package com.example.locstreamserver.model;

import java.util.Objects;

public class Beacon {
    int beaconId;
    String macAddress;
    String beaconName;
    String currentOwner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beacon beacon = (Beacon) o;
        return beaconId == beacon.beaconId && Objects.equals(macAddress, beacon.macAddress) && Objects.equals(beaconName, beacon.beaconName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beaconId, macAddress, beaconName);
    }

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getBeaconName() {
        return beaconName;
    }

    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }

    @Override
    public String toString() {
        return "Beacon{" +
                "beaconId=" + beaconId +
                ", macAddress='" + macAddress + '\'' +
                ", beaconName='" + beaconName + '\'' +
                '}';
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }
}
