package com.example.locstreamserver.model;
import java.time.LocalDateTime;
import java.util.Objects;

public class LocatorNode {
    int locatorId;
    String locatorMAC;
    String locatorName = "Unnamed Locator Node";
    int associatedCameraId;
    int accountId;
    String locatorDescription;
    LocalDateTime dateAdded;

public LocatorNode() {

}
    public LocatorNode(int locatorId, String locatorName, int associatedCameraId, int accountId, String locatorMAC) {
        //change this later to be current number of locator IDs under this account + 1
        if( locatorId == 0 ) {
            throw new IllegalArgumentException("ID cannot be null");
        } else {
            this.locatorId = locatorId;
        }

        this.locatorName = locatorName;
        this.associatedCameraId = associatedCameraId;
        this.accountId = accountId;
        this.locatorMAC = locatorMAC;
    }


    public void addLocator(int locatorId, String locatorName, int associatedCameraId, int accountId, String locatorMAC) {
        //change this later to be current number of locator IDs under this account + 1
        if( locatorId == 0 ) {
            throw new IllegalArgumentException("ID cannot be null");
        } else {
            this.locatorId = locatorId;
        }

        this.locatorName = locatorName;
        this.associatedCameraId = associatedCameraId;
        this.accountId = accountId;
        this.locatorMAC = locatorMAC;
    }



    public int getLocatorId() {
        return locatorId;
    }

    public void setLocatorId(int locatorId) {
        this.locatorId = locatorId;
    }

    public String getLocatorMAC() {
        return locatorMAC;
    }

    public void setLocatorMAC(String locatorMAC) {
        this.locatorMAC = locatorMAC;
    }

    public String getLocatorName() {
        return locatorName;
    }

    public void setLocatorName(String locatorName) {
        this.locatorName = locatorName;
    }

    public int getAssociatedCameraId() {
        return associatedCameraId;
    }

    public void setAssociatedCameraId(int associatedCameraId) {
        this.associatedCameraId = associatedCameraId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getLocatorDescription() {
        return locatorDescription;
    }

    public void setLocatorDescription(String locatorDescription) {
        this.locatorDescription = locatorDescription;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocatorNode that = (LocatorNode) o;
        return locatorId == that.locatorId && associatedCameraId == that.associatedCameraId && accountId == that.accountId && Objects.equals(locatorMAC, that.locatorMAC) && Objects.equals(locatorName, that.locatorName) && Objects.equals(locatorDescription, that.locatorDescription) && Objects.equals(dateAdded, that.dateAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locatorId, locatorMAC, locatorName, associatedCameraId, accountId, locatorDescription, dateAdded);
    }
}
